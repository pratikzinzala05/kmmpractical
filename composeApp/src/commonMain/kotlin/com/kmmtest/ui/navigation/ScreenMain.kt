package com.kmmtest.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.kmmtest.ui.screens.OneComponent
import com.kmmtest.ui.screens.OneComponentImp
import com.kmmtest.ui.screens.TwoComponent
import com.kmmtest.ui.screens.TwoComponentImp
import com.kmmtest.utils.ErrorHandler.errorMsg
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer


@Composable
fun MainScreen(mainComponent: DefaultMainComponent) {

    MaterialTheme() {


        Surface {


            Children(
                stack = mainComponent.childStack,
            ) { child ->

                when (val instance = child.instance) {
                    is DefaultMainComponent.Child.One -> instance.component.Render()

                    is DefaultMainComponent.Child.Two -> instance.component.Render()
                }
            }

            Column(modifier = Modifier.fillMaxSize()) {

                var visible by remember { mutableStateOf(false) }

                if (errorMsg.value != null) {

                    errorMsg.value?.let {

                        visible = true

                        LaunchedEffect(Unit) {

                            delay(2000L)
                            visible = false
                            errorMsg.value = null


                        }


                    }

                }

                AnimatedVisibility(
                    visible = visible,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(10.dp, 15.dp)
                            .background(
                                MaterialTheme.colors.error,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .padding(16.dp)
                    ) {
                        errorMsg.value?.let {
                            Text(text = it, color = Color.White)
                        }
                    }
                }

            }


        }
    }
}

@Composable
fun AppRoot(component: DefaultMainComponent) {
    Children(
        stack = component.childStack,
        modifier = Modifier.fillMaxSize()
    ) {
        when (val instance = it.instance) {
            is DefaultMainComponent.Child.One -> instance.component.Render()
            is DefaultMainComponent.Child.Two -> instance.component.Render()
        }
    }
}


class DefaultMainComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {


    val navigation = StackNavigation<Config>()


    val childStack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = serializer(),
        initialConfiguration = Config.One,
        childFactory = ::createChild,
        handleBackButton = true,
    )


    private fun createChild(configMain: Config, componentContext: ComponentContext): Child =
        when (configMain) {
            Config.One -> Child.One(OneComponentImp(componentContext, startSecond = {

                navigation.push(Config.Two)

            }))

            Config.Two -> Child.Two(TwoComponentImp(componentContext, onNavBack = {

                navigation.pop()

            }))
        }


    sealed class Child {
        data class One(val component: OneComponent) : Child()
        data class Two(val component: TwoComponent) : Child()

    }
}


@Serializable
sealed class Config {
    @Serializable
    data object One : Config()

    @Serializable
    data object Two : Config()

}


object SharedElementManager {
    private val elements = mutableMapOf<String, SharedElementData>()

    fun registerElement(id: String, size: Dp, offset: Offset) {
        elements[id] = SharedElementData(size, offset)
    }

    fun getElement(id: String): SharedElementData? = elements[id]

    data class SharedElementData(
        val size: Dp,
        val offset: Offset
    )
}
