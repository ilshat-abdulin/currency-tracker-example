package com.air.crypto.domain.model

import com.github.mikephil.charting.data.Entry

data class CoinHistory(
    val lowestPrice: Float = 0f,
    val highestPrice: Float = 0f,
    val allPricesPerTime: List<Entry> = emptyList()
)