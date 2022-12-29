package com.air.crypto.data_source.remote.model

data class CoinInfoModel(
    val coins: List<CoinDto> = emptyList(),
    val coinNamesMap: Map<String, String> = emptyMap()
)