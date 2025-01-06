package com.kmmtest

import com.kmmtest.commoninterface.DeviceConfig
import com.kmmtest.diimp.AndroidDeviceConfig
import org.koin.core.module.Module
import org.koin.dsl.module


actual val platformModule: Module = module {

    single<DeviceConfig> { AndroidDeviceConfig() }


}