package com.air.crypto.data_source.remote.model

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