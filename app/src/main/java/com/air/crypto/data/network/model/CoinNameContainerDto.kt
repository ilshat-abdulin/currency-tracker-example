package com.air.crypto.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinNameContainerDto(
    @SerialName("CoinInfo")
    val coinName: CoinNameDto? = null
)