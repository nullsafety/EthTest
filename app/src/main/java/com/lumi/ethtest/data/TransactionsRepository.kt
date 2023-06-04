package com.lumi.ethtest.data

interface TransactionsRepository {
    suspend fun getTransactions(address: String): List<TransactionResponse>
}

class TransactionsRepositoryImpl(private val api: Api) : TransactionsRepository {

    override suspend fun getTransactions(address: String): List<TransactionResponse> =
        api.getTransactions(address).result.take(100)
}