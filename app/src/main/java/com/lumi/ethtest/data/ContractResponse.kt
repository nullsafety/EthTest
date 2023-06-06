package com.lumi.ethtest.data

import com.google.gson.annotations.SerializedName

data class ContractResponse(
    @SerializedName("ABI")
    val abi: String?
)
