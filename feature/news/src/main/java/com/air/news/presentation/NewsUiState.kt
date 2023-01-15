package com.air.news.presentation

import com.air.news.presentation.model.NewsUi

data class NewsUiState(
    val loading: Boolean = true,
    val news: List<NewsUi> = emptyList()
)

