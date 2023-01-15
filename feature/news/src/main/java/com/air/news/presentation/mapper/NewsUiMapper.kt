package com.air.news.presentation.mapper

import com.air.news.domain.model.Article
import com.air.news.presentation.model.NewsUi
import javax.inject.Inject

class NewsUiMapper @Inject constructor() {

    fun mapArticleToUi(article: Article) = NewsUi(
        id = article.id,
        url = article.url,
        imageUrl = article.imageUrl,
        title = article.title,
        body = article.body,
        sourceName = article.sourceName,
        publishedOn = article.publishedOn
    )
}