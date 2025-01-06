package com.kmmtest.di

import com.kmmtest.commoninterface.DeviceConfig
import org.koin.dsl.module

fun initKoinIos(appComponent: IosApplicationComponent) {
    initKoin(
        listOf(module { single { appComponent } })
    )
}


class IosApplicationComponent(val deviceConfig: DeviceConfig)
