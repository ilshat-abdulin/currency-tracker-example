package com.air.crypto.domain.model

data class CoinHistory(
    val lowestPrice: Float = 0f,
    val highestPrice: Float = 0f,
    val pricesOverTime: Map<Long, Double> = emptyMap()
)