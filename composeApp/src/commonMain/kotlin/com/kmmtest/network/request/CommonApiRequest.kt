package com.kmmtest.network.request

import kotlinx.serialization.Serializable

@Serializable
data class CommonApiRequest(
    val id: String? = null,
)