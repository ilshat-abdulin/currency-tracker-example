package com.air.crypto.presentation.coin_detail

import com.air.crypto.presentation.coin_detail.model.CoinHistoryUi

data class CoinHistoryUiState(
    val loading: Boolean = true,
    val coinHistory: CoinHistoryUi = CoinHistoryUi()
)
