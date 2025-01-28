package com.kmmtest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.kmmtest.NavHandler
import com.kmmtest.ui.navigation.SharedElementManager
import com.kmmtest.utils.BaseComponent
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class OneComponentImp(componentContext: ComponentContext, private val startSecond: () -> Unit) :
    BaseComponent(), KoinComponent,
    OneComponent, ComponentContext by componentContext {

    private val navHandler: NavHandler by inject()


    override fun startSecond() = startSecond.invoke()

    @Composable
    override fun Render() {
        ScreenOne(this)
    }

    override fun OpenUrl(url: String) {
        navHandler.openUrl(url)
    }
}


interface OneComponent {

    fun startSecond()

    @Composable
    fun Render()

    fun OpenUrl(url:String)

}


@Composable
fun ScreenOne(component: OneComponent) {



    val sharedElementId = remember { "sharedElement" }
    val sharedElementSize = 100.dp


    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {

                component.OpenUrl("https://stackoverflow.com/questions/2201917/how-can-i-open-a-url-in-androids-web-browser-from-my-application")

            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(sharedElementSize)
                .background(Color.Blue)
                .onGloballyPositioned { layoutCoordinates ->
                    val position = layoutCoordinates.positionInRoot()
                    SharedElementManager.registerElement(
                        sharedElementId,
                        size = sharedElementSize,
                        offset = Offset(position.x, position.y)
                    )
                }
        )
    }


}