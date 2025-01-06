package com.kmmtest.network.datawrapper

data class DataWrapper<T>(val responseBody: T?, val error: String?)

