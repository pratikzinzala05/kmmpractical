package com.kmmtest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.kmmtest.ui.components.BaseButton


class OneComponentImp(componentContext: ComponentContext, private val startSecond: () -> Unit) :
    OneComponent, ComponentContext by componentContext {
    override fun startSecond() = startSecond.invoke()
}


interface OneComponent {

    fun startSecond()

}


@Composable
fun ScreenOne(component: OneComponent) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BaseButton("Click me",onClick = {
            component.startSecond()
        })

    }


}