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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val root = retainedComponent {
                DefaultMainComponent(it)
            }
            MainScreen(root)
        }
    }
}

