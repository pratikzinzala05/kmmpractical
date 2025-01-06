package com.kmmtest.network.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Specification (

  @SerialName("color"    ) var color    : String? = null,
  @SerialName("capacity" ) var capacity : String? = null

)