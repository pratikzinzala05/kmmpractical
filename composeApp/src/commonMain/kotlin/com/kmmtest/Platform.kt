package com.kmmtest

import androidx.compose.runtime.Composable
import org.koin.core.module.Module

expect val platformModule: Module

interface NavHandler {
    fun openUrl(url: String)
}
