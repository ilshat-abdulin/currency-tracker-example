package com.air.news.data.repository

import com.air.core.network.executeRetrofitCall
import com.air.core.network.services.NewsService
import com.air.core_functional.Either
import com.air.core_functional.Failure
import com.air.core_functional.map
import com.air.news.data.mapper.ArticleMapper
import com.air.news.domain.model.Article
import com.air.news.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService,
    private val articleMapper: ArticleMapper
) : NewsRepository {
    override suspend fun getNews(): Either<Failure, List<Article>> {
        return executeRetrofitCall(Dispatchers.IO) {
            newsService.getNews()
        }.map {
            it.articles.map {
                articleMapper.mapDtoToEntity(it)
            }
        }
    }
}