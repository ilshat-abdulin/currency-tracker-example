package com.air.news.domain.model

data class Article(
    val id: String,
    val url: String,
    val imageUrl: String,
    val title: String,
    val body: String,
    val sourceName: String,
    val publishedOn: Long
)