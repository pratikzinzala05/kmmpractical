package com.kmmtest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.ComponentContext
import com.kmmtest.network.UserRepo
import com.kmmtest.network.models.PhoneDetail
import com.kmmtest.ui.components.HeaderWithBackButton
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

    var phoneDetail by remember { mutableStateOf<List<PhoneDetail>?>(null) }
    var isLoading by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {


        isLoading = true
        phoneDetail = component.getPhoneList()
        isLoading = false

    }

    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderWithBackButton {

            component.onNavBack()
        }

        Text(text = phoneDetail.toString())


    }


    isLoading.let {
        if (it) {
            Column(
                modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
                    .background(
                        Color.White
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text("Loading......")

            }

        }


    }


}

