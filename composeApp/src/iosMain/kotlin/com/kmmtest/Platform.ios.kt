package com.kmmtest

import com.kmmtest.commoninterface.DeviceConfig
import com.kmmtest.di.IosApplicationComponent
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.UIKit.UIDevice

actual val platformModule = module {

    single<DeviceConfig> { get<IosApplicationComponent>().deviceConfig }

}
