package com.air.core.network.model.coins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryDataDto (
    val time: Long,
    val high: Double,
    val low: Double,
    val open: Double,
    @SerialName("volumefrom")
    val volumeFrom: Double,
    @SerialName("volumeto")
    val volumeTo: Double,
    val close: Double
)