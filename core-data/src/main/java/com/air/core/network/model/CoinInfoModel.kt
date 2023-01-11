package com.air.core.network.model

data class CoinInfoModel(
    val coins: List<CoinDto> = emptyList(),
    val coinNamesMap: Map<String, String> = emptyMap()
)