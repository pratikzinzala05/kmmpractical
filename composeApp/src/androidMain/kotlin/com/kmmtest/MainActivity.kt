package com.kmmtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.retainedComponent
import com.kmmtest.ui.App
import com.kmmtest.ui.navigation.DefaultMainComponent
import com.kmmtest.ui.navigation.MainScreen
import io.ktor.http.parametersOf
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin

class MainActivity : ComponentActivity() {


    val def = "hello world"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contextProvider: ContextProvider = getKoin().get { parametersOf(this@MainActivity) }

        setContent {

            val root = retainedComponent {
                DefaultMainComponent(it)
            }
            MainScreen(root)
        }
    }
}

