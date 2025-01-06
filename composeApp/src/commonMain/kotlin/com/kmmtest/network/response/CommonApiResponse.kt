package com.kmmtest.network.response


import com.kmmtest.network.models.PhoneDetail
import kotlinx.serialization.Serializable


@Serializable
data class CommonApiResponse(
    val output: Double? = null,
    val phoneList: List<PhoneDetail>? = null
)