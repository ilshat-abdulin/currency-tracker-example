package com.air.crypto.presentation.coin_list

import com.air.crypto.presentation.coin_list.model.CoinItemUi

data class CoinListUiState(
    val loading: Boolean = true,
    val coins: List<CoinItemUi> = emptyList()
)

