package com.lumi.ethtest.presentation.viewmodel

import com.lumi.ethtest.util.decoding.DecodedFunction

data class TransactionUIModel(
    val date: String,
    val senderAddress: String,
    val receiverAddress: String,
    val ethCount: String,
    val direction: String,
    val input: String,
    val decodedFunction: DecodedFunction? = null
)