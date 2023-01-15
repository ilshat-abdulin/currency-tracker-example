package com.air.news.presentation.model

data class NewsUi(
    val id: String = "",
    val url: String = "",
    val imageUrl: String = "",
    val title: String = "",
    val body: String = "",
    val sourceName: String = "",
    val publishedOn: Long = 0
)