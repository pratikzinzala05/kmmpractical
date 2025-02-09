package com.kmmtest.diimp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.kmmtest.ImagePickAndCrop
import com.kmmtest.MainActivity
import java.io.ByteArrayOutputStream

class ImagePickAndCropImpl(private val activity: MainActivity) : ImagePickAndCrop {

    private var onResultCallback: ((ByteArray?) -> Unit)? = null

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var cropImageLauncher: ActivityResultLauncher<CropImageContractOptions>

    fun initialize() {
        // Register pick image launcher
        pickImageLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let { startCrop(it) }
            } else {
                onResultCallback?.invoke(null)
            }
        }

        // Register crop image launcher
        cropImageLauncher = activity.registerForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                val croppedUri = result.uriContent
                croppedUri?.let { uri ->
                    activity.contentResolver.openInputStream(uri)?.use { inputStream ->
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        inputStream.copyTo(byteArrayOutputStream)
                        onResultCallback?.invoke(byteArrayOutputStream.toByteArray())
                    }
                }
            } else {
                onResultCallback?.invoke(null)
            }
        }
    }

    override fun pickAndCropImage(onResult: (ByteArray?) -> Unit) {
        this.onResultCallback = onResult
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        pickImageLauncher.launch(intent)
    }

    private fun startCrop(uri: Uri) {
        cropImageLauncher.launch(
            CropImageContractOptions(
                uri = uri,
                cropImageOptions = CropImageOptions(
                    guidelines = CropImageView.Guidelines.ON,
                    outputCompressFormat = Bitmap.CompressFormat.PNG
                )
            )
        )
    }
}
