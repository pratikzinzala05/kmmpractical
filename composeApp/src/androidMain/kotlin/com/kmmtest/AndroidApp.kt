package com.kmmtest

import android.app.Activity
import android.app.Application
import androidx.activity.ComponentActivity
import com.kmmtest.di.initKoinAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class AndroidApp :Application(){

    private val appModule = module {

        single<NavHandler> { AndroidNavHandler() }
        single<ContextProvider> { (activity: MainActivity) -> AndroidContextProvider(activity) }

    }

    override fun onCreate() {
        super.onCreate()

        initKoinAndroid(listOf(appModule)){
            androidContext(applicationContext)

        }
    }

}