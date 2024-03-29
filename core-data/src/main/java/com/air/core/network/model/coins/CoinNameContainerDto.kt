package com.air.core.network.model.coins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinNameContainerDto(
    @SerialName("CoinInfo")
    val coinName: CoinNameDto? = null
)