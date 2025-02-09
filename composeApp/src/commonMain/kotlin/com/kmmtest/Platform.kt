package com.kmmtest

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.essenty.backhandler.BackHandler
import org.koin.core.module.Module

expect val platformModule: Module

expect fun <C : Any, T : Any> backAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
): StackAnimation<C, T>


expect fun getHelloFromDevice()



expect fun ByteArray.toMyBitmap(): ImageBitmap

interface ImagePickAndCrop {
    fun pickImage(onResult: (ByteArray?) -> Unit)
    fun cropImage(onResult: (ByteArray?) -> Unit)
}
