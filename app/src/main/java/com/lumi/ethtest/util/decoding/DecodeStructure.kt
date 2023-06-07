package com.lumi.ethtest.util.decoding

interface DecodedFunction

data class DecodedTransferFunction(
    val name: String,
    val to: String,
    val amount: String
): DecodedFunction

data class DecodedApproveFunction(
    val name: String,
    val spender: String,
    val amount: String
): DecodedFunction

data class EmptyFunction(
    val name: String = "Function not decoded"
): DecodedFunction