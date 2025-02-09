package com.kmmtest

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.kmmtest.ui.App
import com.kmmtest.ui.navigation.DefaultMainComponent
import com.kmmtest.ui.navigation.MainScreen
import platform.UIKit.UIViewController
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureIcon

val back = BackDispatcher()
val defaultMainComponent = DefaultMainComponent(DefaultComponentContext(ApplicationLifecycle(), backHandler = back))

fun MainViewController() = ComposeUIViewController {


    //rootViewController(rootComponent, backDispatcher = back)

    MainScreen(defaultMainComponent)

   // ScreenJetpackNav()

}



@OptIn(ExperimentalDecomposeApi::class)
fun rootViewController(root: DefaultMainComponent, backDispatcher: BackDispatcher): UIViewController =
    ComposeUIViewController {
        PredictiveBackGestureOverlay(
            backDispatcher = backDispatcher,
            backIcon = { _, _ ->

            },
            modifier = Modifier.fillMaxSize(),
        ) {
            MainScreen(root)
        }
    }