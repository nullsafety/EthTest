package com.lumi.ethtest.data.network

import com.lumi.ethtest.data.ContractResponse
import com.lumi.ethtest.data.EthApiResponse
import com.lumi.ethtest.data.TransactionResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.etherscan.io/"
private const val API_KEY = "B66Q334QBRHETQIBNQ6QPUBPXQSZADRS3C"

fun getApiService(): Api {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)

    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(logging)

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()

    return retrofit.create(Api::class.java)
}

interface Api {

    @GET("api")
    suspend fun getTransactions(
        @Query("address") address: String,
        @Query("module") module: String = "account",
        @Query("action") action: String = "txlist",
        @Query("startblock") startblock: Int = 0,
        @Query("endblock") endblock: Int = 99999999,
        @Query("page") page: Int = 1,
        @Query("offset") offset: Int = 100,
        @Query("sort") sort: String = "desc",
        @Query("apikey") apikey: String = API_KEY,
    ): EthApiResponse<List<TransactionResponse>>

    @GET("api")
    suspend fun getContractSourceCode(
        @Query("address") address: String,
        @Query("module") module: String = "contract",
        @Query("action") action: String = "getsourcecode",
        @Query("apikey") apikey: String = API_KEY,
    ): EthApiResponse<List<ContractResponse>>
}