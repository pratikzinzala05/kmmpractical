package com.kmmtest

import org.koin.core.module.Module

expect val platformModule: Module


interface DeviceConfig{


    fun getDeviceType():String

}