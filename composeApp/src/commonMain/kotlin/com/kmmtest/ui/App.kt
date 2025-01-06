package com.kmmtest.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kmmtest.commoninterface.DeviceConfig
import com.kmmtest.network.UserRepo
import com.kmmtest.network.models.PhoneDetail
import org.koin.compose.koinInject

@Composable
fun App() {
    MaterialTheme {
        val deviceConfig: DeviceConfig = koinInject()
        val repo:UserRepo = koinInject()

        var phoneDetail by remember { mutableStateOf<List<PhoneDetail>?>(null) }

        LaunchedEffect(Unit){

            phoneDetail = repo.getPhoneDetails().responseBody

        }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = phoneDetail.toString())


        }
    }
}