package com.kmmtest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmmtest.themes.colorButton
import com.kmmtest.themes.colorOnPrime
import com.kmmtest.themes.getFontFamily


@Composable
fun BaseButton(
    text: String,
    onClick: () -> Unit,
    contentColor: Color = colorOnPrime,
    backgroundColor: Color = colorButton,
    topStart: Dp = 15.dp,
    topEnd: Dp = 15.dp,
    bottomStart: Dp = 15.dp,
    bottomEnd: Dp = 15.dp,modifier: Modifier = Modifier

) {

    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 5.dp).background(
            color = backgroundColor,
            shape = RoundedCornerShape(topStart, topEnd, bottomEnd, bottomStart)
        ).clickable() {

            onClick()

        }.padding(vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(text = text, color = contentColor, fontSize = 18.sp, fontFamily = getFontFamily().bold)
    }
}