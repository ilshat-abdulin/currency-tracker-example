package com.air.coins.presentation.coin_detail

import com.air.coins.presentation.coin_detail.model.CoinDetailUi
import com.air.coins.presentation.coin_detail.model.CoinHistoryUi

data class CoinDetailUiState(
    val loading: Boolean = true,
    val coinHistory: CoinHistoryUi = CoinHistoryUi(),
    val coinDetail: CoinDetailUi = CoinDetailUi()
)
