package com.lumi.ethtest.data

import com.lumi.ethtest.data.network.Api
import com.lumi.ethtest.data.network.ApiResult
import com.lumi.ethtest.data.network.doWithCatching

interface TransactionsRepository {
    suspend fun getTransactions(address: String): ApiResult<EthApiResponse<List<TransactionResponse>>>
}

class TransactionsRepositoryImpl(private val api: Api) : TransactionsRepository {
    override suspend fun getTransactions(address: String): ApiResult<EthApiResponse<List<TransactionResponse>>> =
        doWithCatching { api.getTransactions(address) }
}