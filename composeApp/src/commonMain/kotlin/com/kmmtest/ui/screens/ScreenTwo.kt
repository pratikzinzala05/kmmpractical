package com.kmmtest.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.input.PlatformImeOptions
import androidx.navigation.NavController
import com.arkivanov.decompose.ComponentContext
import com.kmmtest.network.UserRepo
import com.kmmtest.network.models.PhoneDetail
import com.kmmtest.toMyBitmap
import com.kmmtest.ui.components.BaseButton
import com.kmmtest.ui.components.HeaderWithBackButton
import com.kmmtest.utils.BaseComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class TwoComponentImp(
    componentContext: ComponentContext,
    private val onNavBack: () -> Unit,
    override val byteArray: ByteArray,
) :
    BaseComponent(), TwoComponent, ComponentContext by componentContext, KoinComponent {

    private val userRepo: UserRepo by inject()

    override fun onNavBack() = onNavBack.invoke()
    override suspend fun getPhoneList(): List<PhoneDetail>? =
        withIO(userRepo.getPhoneDetails())

    @Composable
    override fun Render() {
        ScreenTwo(this)
    }
}


interface TwoComponent {

    val byteArray: ByteArray
    fun onNavBack()

    suspend fun getPhoneList(): List<PhoneDetail>?

    @Composable
    fun Render()

}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ScreenTwo(component: TwoComponent) {

    println(
        "********************************************" +
                "bytearry height ${component.byteArray.decodeToImageBitmap()}" +
                "byte size is ${component.byteArray.size}" +
                "image bitmap is ${component.byteArray.decodeToImageBitmap()}" +
                "********************************************"
    )

    Box(modifier = Modifier.fillMaxSize().background(color = Color.White)) {

        Column(
            modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HeaderWithBackButton {

                component.onNavBack()
            }
            Image(
                bitmap = component.byteArray.toMyBitmap(),
                contentDescription = "",
                modifier = Modifier.fillMaxSize().background(color = Color.Gray)
            )


        }


    }

}


