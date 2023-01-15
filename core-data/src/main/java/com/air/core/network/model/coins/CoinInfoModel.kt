package com.air.core.network.model.coins

data class CoinInfoModel(
    val coins: List<CoinDto> = emptyList(),
    val coinNamesMap: Map<String, String> = emptyMap()
)