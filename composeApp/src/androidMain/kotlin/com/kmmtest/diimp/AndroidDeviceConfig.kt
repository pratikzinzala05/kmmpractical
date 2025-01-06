package com.kmmtest.diimp

import com.kmmtest.commoninterface.DeviceConfig

class AndroidDeviceConfig: DeviceConfig {
    override fun getDeviceType(): String = "Android"
}