package com.kmmtest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            AsyncImage(
                model = "https://firebasestorage.googleapis.com/v0/b/zen-meditation-275b2.appspot.com/o/COMMON_MUSIC%2F1728556842276.jpg?alt=media&token=fed1639a-657b-475a-a421-261f4ede6b1d",
                contentDescription = "",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}