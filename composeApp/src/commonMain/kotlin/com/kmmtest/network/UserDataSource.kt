package com.kmmtest.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import com.kmmtest.network.datawrapper.BaseDataSource
import com.kmmtest.network.datawrapper.DataWrapper
import com.kmmtest.network.models.PhoneDetail
import com.kmmtest.network.response.CommonApiResponse

class UserDataSource(private val httpClient: HttpClient) : UserRepo, BaseDataSource() {

    override suspend fun getPhoneDetails(): DataWrapper<List<PhoneDetail>> {
        return execute<List<PhoneDetail>> {
            httpClient.get(EndPoint.GET_PHONE_DETAILS)
        }
    }


}