package com.air.coins.domain.model

data class CoinItem(
    val fromSymbol: String,
    val toSymbol: String?,
    val currentPrice: String?,
    val lastUpdate: String,
    val highDay: String?,
    val lowDay: String?,
    val lastMarket: String?,
    val imageUrl: String?,
    val fullName: String?
)