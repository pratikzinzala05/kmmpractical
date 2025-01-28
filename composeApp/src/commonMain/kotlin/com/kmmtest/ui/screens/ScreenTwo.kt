package com.kmmtest.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.kmmtest.network.UserRepo
import com.kmmtest.network.models.PhoneDetail
import com.kmmtest.ui.components.HeaderWithBackButton
import com.kmmtest.ui.navigation.SharedElementManager
import com.kmmtest.utils.BaseComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class TwoComponentImp(componentContext: ComponentContext, private val onNavBack: () -> Unit) :
    BaseComponent(), TwoComponent, ComponentContext by componentContext, KoinComponent {

    private val userRepo: UserRepo by inject()

    override fun onNavBack() = onNavBack.invoke()
    override suspend fun getPhoneList(): List<PhoneDetail>? =
        withIO(userRepo.getPhoneDetails())

    @Composable
    override fun Render() {
        ScreenTwo(this)
    }
}


interface TwoComponent {

    fun onNavBack()

    suspend fun getPhoneList(): List<PhoneDetail>?

    @Composable
    fun Render()

}


@Composable
fun ScreenTwo(component: TwoComponent) {

    val sharedElementId = remember { "sharedElement" }
    val targetSize = 200.dp
    val targetOffset = Offset(0f, 0f) // Centered in the screen

    // Get the initial shared element data
    val element = SharedElementManager.getElement(sharedElementId)

    // Animate size
    val animatedSize = animateDpAsState(
        targetValue = targetSize,
        animationSpec = tween(durationMillis = 600)
    )

    // Animate position
    val animatedOffsetX = animateDpAsState(
        targetValue = (targetOffset.x - (element?.offset?.x ?: 0f)).dp,
        animationSpec = tween(durationMillis = 600)
    )
    val animatedOffsetY = animateDpAsState(
        targetValue = (targetOffset.y - (element?.offset?.y ?: 0f)).dp,
        animationSpec = tween(durationMillis = 600)
    )

    LaunchedEffect(Unit) {
        // Clear the shared element manager after animation
        SharedElementManager.registerElement(sharedElementId, targetSize, targetOffset)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(animatedOffsetX.value.roundToPx(), animatedOffsetY.value.roundToPx())
                }
                .size(animatedSize.value)
                .background(Color.Blue)
        )
    }

}

