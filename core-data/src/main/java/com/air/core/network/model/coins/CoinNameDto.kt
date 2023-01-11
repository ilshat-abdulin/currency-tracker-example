package com.air.core.network.model.coins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinNameDto(
    @SerialName("Name")
    val name: String? = null,
    @SerialName("FullName")
    val fullName: String? = null,
)