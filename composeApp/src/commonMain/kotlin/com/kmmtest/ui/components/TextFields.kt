package com.kmmtest.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.kmmtest.themes.colorPrime
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun BaseTextField(
    hint: String,
    textValue: String,
    onTextValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next

    ),
    maxLength: Int = Int.MAX_VALUE
) {

//    val textFiled by remember { textValue }
    var hasFocus by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = textValue,
        onValueChange = {

            if (it.length <= maxLength) onTextValueChanged(it)
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp, vertical = 0.dp).border(
            width = 1.dp,
            color = if (hasFocus) colorPrime else Color.Gray,
            shape = RoundedCornerShape(10.dp)
        ).onFocusChanged { focusState -> hasFocus = focusState.hasFocus },
        placeholder = {
            Text(
                text = hint,
            )
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Unspecified,
            unfocusedBorderColor = Color.Unspecified,
            cursorColor = colorPrime,
        ),
        singleLine = true,
        maxLines = 1, keyboardOptions = keyboardOptions,
        )
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun BaseTextFieldPassword(
    hint: String,
    textValue: MutableState<String>,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
    )
) {

    val textFiled by remember { textValue }
    var hasFocus by remember { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }



    OutlinedTextField(
        value = textFiled,
        onValueChange = {
            textValue.value = it
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp, vertical = 0.dp).border(
            width = 1.dp,
            color = if (hasFocus) colorPrime else Color.Gray,
            shape = RoundedCornerShape(10.dp)
        ).onFocusChanged { focusState -> hasFocus = focusState.hasFocus },

        placeholder = {
            Text(
                text = hint,
            )
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Unspecified,
            unfocusedBorderColor = Color.Unspecified,
        ),
        singleLine = true,
        maxLines = 1, keyboardOptions = keyboardOptions,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
//            val image = if (passwordVisible)
//                painterResource(Res.drawable.ic_eye_close)
//            else
//                painterResource(Res.drawable.ic_eye_open)
//
//            IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                Icon(Icons.Rounded., contentDescription = "Visibility Toggle")
//            }
        }
    )
}
