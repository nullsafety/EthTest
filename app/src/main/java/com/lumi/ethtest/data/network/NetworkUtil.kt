package com.lumi.ethtest.data.network

fun <T> ApiResult<T>.isSuccess(action: (ApiResult.Success<T>) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) {
        action(this)
    }
    return this
}

fun <T> ApiResult<T>.isError(action: (ApiResult.Error<T>) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) {
        action(this)
    }
    return this
}

inline fun <T> doWithCatching(
    doWork: () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(doWork.invoke())
    } catch (e: Throwable) {
        ApiResult.Error(e.message ?: "error")
    }
}