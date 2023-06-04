package com.lumi.ethtest.data

data class EthApiResponse<T>(
    val status: String,
    val message: String,
    val result: T
)

