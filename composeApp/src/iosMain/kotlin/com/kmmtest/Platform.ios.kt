package com.kmmtest

import org.koin.core.module.Module
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
actual val platformModule: Module
    get() = TODO("Not yet implemented")