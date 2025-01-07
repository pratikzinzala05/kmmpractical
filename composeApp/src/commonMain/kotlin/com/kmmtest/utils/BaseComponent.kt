package com.kmmtest.utils

import com.kmmtest.network.datawrapper.DataWrapper
import com.kmmtest.utils.ErrorHandler.errorMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

open class BaseComponent{


    suspend fun <T> withIO(
        block: DataWrapper<T>
    ): T? =
        withContext(Dispatchers.IO) {
            errorMsg.value = null

            block.let {
                if (it.error == null) {

                    return@withContext it.responseBody
                } else {
                    errorMsg.value = it.error

                    return@withContext null
                }

            }


        }
}
