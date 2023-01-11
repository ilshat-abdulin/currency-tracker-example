package com.air.core.network.model.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("Data")
    val articles: List<ArticleDto>
)