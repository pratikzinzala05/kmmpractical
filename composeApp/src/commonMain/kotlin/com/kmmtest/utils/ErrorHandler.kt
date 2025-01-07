package com.kmmtest.utils

import androidx.compose.runtime.mutableStateOf

object ErrorHandler {
    var errorMsg = mutableStateOf<String?>(null)
    fun showError(msg: String){
        errorMsg.value = msg
    }

}
