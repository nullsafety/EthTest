package com.lumi.ethtest.data.network

sealed class ApiResult<T> {

    class Success<T>(
        val data: T
    ) : ApiResult<T>()

    class Error<T>(
        val error: String
    ) : ApiResult<T>()
}