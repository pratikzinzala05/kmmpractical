package com.kmmtest.network.datawrapper

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

open class BaseDataSource {

    suspend inline fun <reified T> execute(callAPI: () -> HttpResponse): DataWrapper<T> {
        return try {
            val result = callAPI()

            if (result.status.isSuccess()) {
                val response = result.body<T>()
                DataWrapper(response, null)

            } else {
                val response = result.body<String>()
                //DataWrapper(null, result.status.description)
                throw Exception(response)
            }

        } catch (e: Exception) {
            DataWrapper(null, e.message)
        }
    }

}