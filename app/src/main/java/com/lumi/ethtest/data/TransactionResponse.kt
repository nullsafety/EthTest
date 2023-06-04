package com.lumi.ethtest.data

import com.lumi.ethtest.presentation.viewmodel.TransactionUIModel
import com.lumi.ethtest.ui.util.weiToEther

data class TransactionResponse(
    val blockNumber: String,
    val timeStamp: String,
    val hash: String,
    val nonce: String,
    val blockHash: String,
    val transactionIndex: String,
    val from: String,
    val to: String,
    val value: String,
    val gas: String,
    val gasPrice: String,
    val isError: String,
    val txreceipt_status: String,
    val input: String,
    val contractAddress: String,
    val cumulativeGasUsed: String,
    val gasUsed: String,
    val confirmations: String,
    val methodId: String,
    val functionName: String
) {
    fun toTransactionUI(address: String) = TransactionUIModel(
        date = timeStamp.toLong().plus(confirmations.toLong()).toString(),
        senderAddress = from,
        receiverAddress = to,
        ethCount = value.weiToEther(),
        direction = if (from == address) "out" else "in",
        input = input
    )
}