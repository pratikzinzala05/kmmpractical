package com.kmmtest.ui.navigation

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.Value
import com.kmmtest.ui.screens.OneComponent
import com.kmmtest.ui.screens.OneComponentImp
import com.kmmtest.ui.screens.ScreenOne
import com.kmmtest.ui.screens.ScreenTwo
import com.kmmtest.ui.screens.TwoComponent
import com.kmmtest.ui.screens.TwoComponentImp
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer


@Composable
fun MainScreen(mainComponent: DefaultMainComponent) {

    MaterialTheme() {


        Surface {


            Children(
                stack = mainComponent.childStack,
                animation = stackAnimation(fade() + slide())
            ) { child ->

                when (val instance = child.instance) {
                    is DefaultMainComponent.Child.One -> ScreenOne(
                        component = instance.component
                    )

                    is DefaultMainComponent.Child.Two -> ScreenTwo(component = instance.component)
                }
            }
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

                navigation.pushNew(Config.Two)

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
    data object Two:Config()

}


