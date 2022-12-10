package com.air.crypto.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinHistoryResponse(
    @SerializedName("Data")
    @Expose
    val data: CoinHistoryDataListDto? = null
)