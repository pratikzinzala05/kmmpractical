package com.kmmtest

import android.app.Activity
import android.app.Application
import com.kmmtest.di.initKoinAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class AndroidApp :Application(){

    private val appModule = module {
        scope<Activity> {
            scoped { get<Activity>() }
        }
    }

    override fun onCreate() {
        super.onCreate()

        initKoinAndroid(listOf(appModule)){
            androidContext(applicationContext)
        }
    }

}