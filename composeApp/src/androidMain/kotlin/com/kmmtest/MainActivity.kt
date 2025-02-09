package com.kmmtest

import android.app.Activity
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.retainedComponent
import com.kmmtest.di.initKoinAndroid
import com.kmmtest.diimp.ImagePickAndCropImpl
import com.kmmtest.ui.navigation.DefaultMainComponent
import com.kmmtest.ui.navigation.MainScreen
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.math.sin

class MainActivity : ComponentActivity() {

    private val appModule = module {
        scope<Activity> {
            scoped { get<Activity>() }
        }

//        single<ContextProvider> { (activity: MainActivity) -> AndroidContextProvider(activity) }
        single<ImagePickAndCrop> { ImagePickAndCropImpl(this@MainActivity) }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKoinAndroid(listOf(appModule)){
            androidContext(applicationContext)
        }
        val imagePickAndCrop:ImagePickAndCrop by inject()
        (imagePickAndCrop as ImagePickAndCropImpl).initialize()
        setContent {

//            val contextProvider: ContextProvider = getKoin().get { parametersOf(this@MainActivity) }

            val root = retainedComponent {
                DefaultMainComponent(it)
            }
            MainScreen(root)
        }
    }
}

