package com.kmmtest.network

import com.kmmtest.network.datawrapper.DataWrapper
import com.kmmtest.network.models.PhoneDetail
import com.kmmtest.network.response.CommonApiResponse

interface UserRepo {

    suspend fun getPhoneDetails(): DataWrapper<List<PhoneDetail>>


}