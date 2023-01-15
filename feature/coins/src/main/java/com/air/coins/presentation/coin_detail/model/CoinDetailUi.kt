package com.air.coins.presentation.coin_detail.model

data class CoinDetailUi(
    val fromSymbol: String = "",
    val fullName: String = "",
    val currentPrice: String = "",
    val lastUpdate: String = "",
    val imageUrl: String = "",
    val highDay: String = "",
    val lowDay: String = "",
    val lastMarket: String = "",
)
