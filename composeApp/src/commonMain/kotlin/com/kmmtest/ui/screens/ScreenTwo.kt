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
import com.kmmtest.ui.components.HeaderWithBackButton


class TwoComponentImp(componentContext: ComponentContext,private val onNavBack:()->Unit) :TwoComponent,ComponentContext by componentContext{
    override fun onNavBack() = onNavBack.invoke()
}


interface TwoComponent{

    fun onNavBack()

}


@Composable
fun ScreenTwo(component: TwoComponent){

    Column(
            modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderWithBackButton {

            component.onNavBack()
        }

            Text(text = "Two")


    }


}