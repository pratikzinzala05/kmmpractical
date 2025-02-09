package com.kmmtest.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.navigation.NavController
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.kmmtest.ImagePickAndCrop
import com.kmmtest.toMyBitmap
import com.kmmtest.ui.components.BaseButton
import com.kmmtest.utils.BaseComponent
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerType
import kotlinx.coroutines.launch
import network.chaintech.cmpimagepickncrop.CMPImagePickNCropDialog
import network.chaintech.cmpimagepickncrop.imagecropper.rememberImageCropper
import org.koin.compose.koinInject


class OneComponentImp(componentContext: ComponentContext, private val startSecond: (ByteArray) -> Unit) :
    BaseComponent(),
    OneComponent, ComponentContext by componentContext {

    private val _email = MutableValue("")
    override val email: Value<String> get() = _email

    override fun startSecond(byteArray: ByteArray) = startSecond.invoke(byteArray)
    @Composable
    override fun Render() {
        ScreenOne(this)
    }

    override fun updateEmail(email: String) {
        _email.value = email

    }
}


interface OneComponent {

    val email: Value<String>

    fun startSecond(byteArray: ByteArray)

    @Composable
    fun Render()

    fun updateEmail(email:String)

}



@Composable
fun ScreenOne(component: OneComponent) {

    val imageCropper = rememberImageCropper()
    var selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }
    var openImagePicker by remember { mutableStateOf(value = false) }



    CMPImagePickNCropDialog(
        imageCropper = imageCropper,
        openImagePicker = openImagePicker,
        autoZoom = true,
        imagePickerDialogHandler = {
            openImagePicker = it
        },
        selectedImageCallback = {
            selectedImage = it
        })

    val coroutineScope = rememberCoroutineScope()

    var selectedImageData by remember { mutableStateOf<ByteArray?>(null) }
    var image by remember { mutableStateOf<ImageBitmap?>(null) }

    val imagePickerLauncher = rememberFilePickerLauncher(PickerType.Image) { selectedImage ->
        coroutineScope.launch {
            val bytes = selectedImage?.readBytes()
            selectedImageData = bytes
            image = selectedImageData?.toMyBitmap()


        }
    }

    val imagePickAndCrop:ImagePickAndCrop = koinInject()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        selectedImage?.let {
            Image(bitmap = it, contentDescription = "", modifier = Modifier.fillMaxWidth().aspectRatio(1f))

        }
        BaseButton(text = "Pick Image", onClick = {
           // imagePickerLauncher.launch()

            imagePickAndCrop.pickImage {
                selectedImageData = it
                selectedImage = it?.toMyBitmap()

            }

        })
        BaseButton(text = "Crop Image", onClick = {
           // imagePickerLauncher.launch()


            imagePickAndCrop.cropImage {
                selectedImageData = it
                selectedImage = it?.toMyBitmap()

            }
        })


        BaseButton("Click me",onClick = {

            selectedImageData?.let {
                component.startSecond(it)

            }


        })

    }



}


@Composable
fun ScreenA(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        BaseButton("Click me",onClick = {
            navController.navigate("B")
        })

    }



}