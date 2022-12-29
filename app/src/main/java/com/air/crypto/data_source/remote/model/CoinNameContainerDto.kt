package com.air.crypto.data_source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinNameContainerDto(
    @SerialName("CoinInfo")
    val coinName: CoinNameDto? = null
)