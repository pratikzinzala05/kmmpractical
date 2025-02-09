package com.kmmtest.diimp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.kmmtest.ImagePickAndCrop
import com.kmmtest.MainActivity
import com.yalantis.ucrop.UCrop
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ImagePickAndCropImpl(private val activity: MainActivity) : ImagePickAndCrop {

    private var pickImageCallback: ((ByteArray?) -> Unit)? = null
    private var cropImageCallback: ((ByteArray?) -> Unit)? = null

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var cropImageLauncher: ActivityResultLauncher<Intent>

    private var selectedImageByteArray:ByteArray? = null
    fun initialize() {
        // Image Picker
        pickImageLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                uri?.let {
                    val byteArray = getByteArrayFromUri(it)
                    selectedImageByteArray = byteArray
                    pickImageCallback?.invoke(byteArray)
                } ?: pickImageCallback?.invoke(null)
            } else {
                pickImageCallback?.invoke(null)
            }
        }

        // Image Cropper
        cropImageLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = UCrop.getOutput(result.data!!)
                uri?.let {
                    val byteArray = getByteArrayFromUri(it)
                    cropImageCallback?.invoke(byteArray)
                } ?: cropImageCallback?.invoke(null)
            } else {
                cropImageCallback?.invoke(null)
            }
        }
    }

    override fun pickImage(onResult: (ByteArray?) -> Unit) {
        pickImageCallback = onResult
        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
        pickImageLauncher.launch(intent)
    }

    override fun cropImage(onResult: (ByteArray?) -> Unit) {
        cropImageCallback = onResult

        if (selectedImageByteArray != null) {
            val bitmap = BitmapFactory.decodeByteArray(selectedImageByteArray, 0, selectedImageByteArray!!.size)

            val sourceUri = getUriFromBitmap(bitmap)
            val destUri = getUriFromBitmap(bitmap)

            activity.grantUriPermission(activity.packageName, sourceUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            activity.grantUriPermission(activity.packageName, destUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

            UCrop.of(sourceUri, destUri)
                .withMaxResultSize(1000, 1000)
                .start(activity, cropImageLauncher)
        } else {
            cropImageCallback?.invoke(null)
        }
    }



    private fun getByteArrayFromUri(uri: Uri): ByteArray? {
        return activity.contentResolver.openInputStream(uri)?.use { inputStream ->
            val outputStream = ByteArrayOutputStream()
            inputStream.copyTo(outputStream)
            outputStream.toByteArray()
        }
    }

    private fun getUriFromBitmap(bitmap: Bitmap): Uri {
        val cacheDir = activity.cacheDir
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }

        val file = File(cacheDir, "temp_dp_img.png")

        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }


        if (!file.exists() || file.length() == 0L) {
            throw IllegalStateException("File was not created properly!")
        }

        return FileProvider.getUriForFile(activity, "${activity.packageName}.provider", file)
    }

}
