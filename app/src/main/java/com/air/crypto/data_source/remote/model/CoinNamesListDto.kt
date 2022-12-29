package com.air.crypto.data_source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinNamesListDto(
    @SerialName("Data")
    val names: List<CoinNameContainerDto>? = null
)