package com.lumi.ethtest.util.decoding

sealed class DecodingFunctionName(
    val signature: String,
    val name: String
) {
    object Transfer : DecodingFunctionName(signature = "0xa9059cbb", name = "transfer")
    object Approve : DecodingFunctionName(signature = "0x095ea7b3", name = "approve")
}