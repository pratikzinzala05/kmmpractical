package com.kmmtest.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.kmmtest.themes.colorOnPrime
import com.kmmtest.ui.icons.BackArrow
import com.zenlevitation.extention.baseClick

@Composable
fun HeaderWithBackButton(onBackPress: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth().height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            imageVector = BackArrow, contentDescription = "",
            modifier = Modifier.fillMaxHeight().padding(horizontal = 25.dp).baseClick {
                onBackPress()
            },
        )

    }


}
