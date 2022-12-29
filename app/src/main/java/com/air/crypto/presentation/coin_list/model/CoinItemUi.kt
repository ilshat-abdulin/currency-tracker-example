package com.air.crypto.presentation.coin_list.model

data class CoinItemUi(
    val fromSymbol: String,
    val fullName: String,
    val currentPrice: String,
    val lastUpdate: String,
    val imageUrl: String
)