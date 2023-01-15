package com.air.core.network.model.news

import kotlinx.serialization.Serializable

@Serializable
data class SourceInfoDto(
    val name: String,
    val img: String
)

