package com.air.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryResponse(
    @SerialName("Data")
    val data: CoinHistoryDataListDto? = null
)