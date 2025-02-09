package com.kmmtest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.essenty.backhandler.BackHandler
import com.kmmtest.commoninterface.DeviceConfig
import com.kmmtest.diimp.AndroidDeviceConfig
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.ByteArrayOutputStream


actual val platformModule: Module = module {

    single<DeviceConfig> { AndroidDeviceConfig() }


}

actual fun <C : Any, T : Any> backAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
): StackAnimation<C, T>  =
    predictiveBackAnimation(
        backHandler = backHandler,
        fallbackAnimation = stackAnimation(),
        selector = { initialBackEvent, _, _ ->
            predictiveBackAnimatable(
                initialBackEvent = initialBackEvent,
                exitModifier = { progress, _ ->
                    Modifier
                },
                enterModifier = { progress, _ -> Modifier},
            )
        },
        onBack = onBack,
    )

actual fun getHelloFromDevice() {
}

actual fun ByteArray.toMyBitmap(): ImageBitmap {
    val bitmap = BitmapFactory.decodeByteArray(this, 0, this.size)
    return bitmap?.asImageBitmap() ?: throw IllegalArgumentException("Failed to decode bitmap")
}

class AndroidContextProvider(private val mainActivity: MainActivity) : ContextProvider {
    override fun getMainActivity(): MainActivity {
        return mainActivity
    }

}


interface ContextProvider {
    fun getMainActivity(): MainActivity
}