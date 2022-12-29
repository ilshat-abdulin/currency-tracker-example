package com.air.crypto.data_source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class CoinJsonContainerDto(
    @SerialName("RAW")
    val json: JsonObject? = null
)