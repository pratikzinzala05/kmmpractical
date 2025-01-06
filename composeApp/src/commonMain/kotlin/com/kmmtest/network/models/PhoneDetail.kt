package com.kmmtest.network.models


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class PhoneDetail (
  @SerialName("id"   ) var id   : String? = null,
  @SerialName("name" ) var name : String? = null,
  @SerialName("data" ) var specification: Specification? = null

)