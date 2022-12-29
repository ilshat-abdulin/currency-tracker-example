package com.air.crypto.data_source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinNameDto(
    @SerialName("Name")
    val name: String? = null,
    @SerialName("FullName")
    val fullName: String? = null,
)