package com.air.crypto.presentation.coin_list

import com.air.crypto.domain.model.CoinInfo

data class CoinListUiState(
    val loading: Boolean = true,
    val coins: List<CoinInfo> = emptyList()
)

