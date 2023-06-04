package com.lumi.ethtest.presentation.viewmodel

data class TransactionUIModel(
    val date: String,
    val senderAddress: String,
    val receiverAddress: String,
    val ethCount: String,
    val direction: String,
    val input: String
)