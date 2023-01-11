package com.air.core.network.model.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val id: String,
    val guid: String,
    @SerialName("published_on")
    val publishedOn: Long,
    @SerialName("imageurl")
    val imageUrl: String,
    val title: String,
    val url: String,
    val body: String,
    val tags: String,
    @SerialName("upvotes")
    val upVotes: String,
    @SerialName("downvotes")
    val downVotes: String,
    val categories: String,
    val sourceInfo: SourceInfoDto,
    val source: String
)
