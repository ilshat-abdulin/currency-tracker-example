package com.air.news.data.mapper

import com.air.core.network.model.news.ArticleDto
import com.air.news.domain.model.Article
import javax.inject.Inject

class ArticleMapper @Inject constructor() {
    fun mapDtoToEntity(dto: ArticleDto) = Article(
        id = dto.id.orEmpty(),
        url = dto.url.orEmpty(),
        imageUrl = dto.imageUrl.orEmpty(),
        title = dto.title.orEmpty(),
        body = dto.body.orEmpty(),
        sourceName = dto.sourceInfo?.name.orEmpty(),
        publishedOn = dto.publishedOn.takeIf { it != null } ?: 0L
    )
}