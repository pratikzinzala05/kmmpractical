package com.kmmtest.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.backhandler.BackHandler
import com.kmmtest.backAnimation
import com.kmmtest.ui.screens.OneComponent
import com.kmmtest.ui.screens.OneComponentImp
import com.kmmtest.ui.screens.ScreenA
import com.kmmtest.ui.screens.ScreenOne
import com.kmmtest.ui.screens.ScreenTwo
import com.kmmtest.ui.screens.TwoComponent
import com.kmmtest.ui.screens.TwoComponentImp
import com.kmmtest.utils.ErrorHandler.errorMsg
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer


@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun MainScreen(mainComponent: DefaultMainComponent) {



        Surface {

            Children(
                stack = mainComponent.childStack,
                animation = backAnimation(
                    backHandler = mainComponent.backHandler,
                    onBack = mainComponent::onBack,
                )
            ) { child ->
                when (val instance = child.instance) {
                    is DefaultMainComponent.Child.One -> ScreenOne(
                        component = instance.component
                    )

                    is DefaultMainComponent.Child.Two -> ScreenTwo(component = instance.component)
                }
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
                            Color.Red,
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


class DefaultMainComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext {


    val navigation = StackNavigation<Config>()


    val childStack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = serializer(),
        initialConfiguration = Config.One,
        childFactory = ::createChild,
        handleBackButton = true,
    )

    fun onBack() {
        navigation.pop()
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createChild(configMain: Config, componentContext: ComponentContext): Child =
        when (configMain) {
            Config.One -> Child.One(OneComponentImp(componentContext, startSecond = {

                navigation.push(Config.Two(it))

            }))

            is Config.Two -> Child.Two(TwoComponentImp(componentContext, onNavBack = {
                navigation.pop()
            },configMain.byteArray))


        }

    fun pop() {
        navigation.pop()
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
    data class Two(val byteArray: ByteArray) : Config()

}


