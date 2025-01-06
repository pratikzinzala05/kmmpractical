package com.kmmtest.diimp

import com.kmmtest.DeviceConfig

class AndroidDeviceConfig:DeviceConfig {
    override fun getDeviceType(): String = "Android"
}