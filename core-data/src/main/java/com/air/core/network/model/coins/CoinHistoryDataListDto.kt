package com.air.core.network.model.coins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryDataListDto(
    @SerialName("TimeFrom")
    val timeFrom: Long,
    @SerialName("TimeTo")
    val timeTo: Long,
    @SerialName("Data")
    val data: List<CoinHistoryDataDto>? = null
)
