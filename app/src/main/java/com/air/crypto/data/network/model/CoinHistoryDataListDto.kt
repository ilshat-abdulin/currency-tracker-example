package com.air.crypto.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinHistoryDataListDto(
    val aggregated: Boolean,
    val timeFrom: Long,
    val timeTo: Long,
    @SerializedName("Data")
    @Expose
    val data: List<CoinHistoryDataDto>? = null
)
