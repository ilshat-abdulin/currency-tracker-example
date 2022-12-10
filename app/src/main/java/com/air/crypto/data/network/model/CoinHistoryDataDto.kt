package com.air.crypto.data.network.model

data class CoinHistoryDataDto (
    val time: Long,
    val high: Double,
    val low: Double,
    val open: Double,
    val volumefrom: Double,
    val volumeto: Double,
    val close: Double,
    val conversionSymbol: String
)