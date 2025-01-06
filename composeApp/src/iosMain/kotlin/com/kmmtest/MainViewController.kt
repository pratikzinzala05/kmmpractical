package com.kmmtest

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.kmmtest.ui.App
import com.kmmtest.ui.navigation.DefaultMainComponent
import com.kmmtest.ui.navigation.MainScreen

fun MainViewController() = ComposeUIViewController {

    val rootComponent = remember {
        DefaultMainComponent(DefaultComponentContext(ApplicationLifecycle()))
    }

    MainScreen(rootComponent)


}