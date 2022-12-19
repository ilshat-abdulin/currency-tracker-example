package com.air.crypto.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class CoinInfoJsonContainerDto(
    @SerialName("RAW")
    val json: JsonObject? = null
)