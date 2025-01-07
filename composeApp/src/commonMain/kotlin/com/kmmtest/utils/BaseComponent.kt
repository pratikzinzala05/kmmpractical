package com.kmmtest.utils

import com.kmmtest.network.datawrapper.DataWrapper
import com.kmmtest.utils.ErrorHandler.errorMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

open class BaseComponent{

    private val _isLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow = _isLoadingFlow.asStateFlow()


    suspend fun <T> withIO(
        shouldShowLoading: Boolean = true,
        block: DataWrapper<T>
    ): T? =
        withContext(Dispatchers.IO) {
            errorMsg.value = null
            // _errorMessagesFlow.emit(null)
            if (shouldShowLoading) {
                _isLoadingFlow.value = true
            }


            block.let {
                if (it.error == null) {
                    if (shouldShowLoading) {
                        _isLoadingFlow.value = false
                    }
                    return@withContext it.responseBody
                } else {
                    // _errorMessagesFlow.emit(it.error)
                    errorMsg.value = it.error
                    if (shouldShowLoading) {
                        _isLoadingFlow.value = false
                    }
                    return@withContext null
                }

            }


        }
}
