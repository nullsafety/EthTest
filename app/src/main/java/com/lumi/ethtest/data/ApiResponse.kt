package com.lumi.ethtest.data

data class ApiResponse<T>(
    val status: String,
    val message: String,
    val result: T
)