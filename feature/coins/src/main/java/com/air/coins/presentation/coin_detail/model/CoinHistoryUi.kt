package com.air.coins.presentation.coin_detail.model

import com.github.mikephil.charting.data.Entry

data class CoinHistoryUi(
    val pricesOverTime: List<Entry> = emptyList(),
    val lowestValue: Float = 0f,
    val highestValue: Float = 0f
)
