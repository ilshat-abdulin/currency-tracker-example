package com.air.news.domain.repository

import com.air.core_functional.Either
import com.air.core_functional.Failure
import com.air.news.domain.model.Article

interface NewsRepository {
    suspend fun getNews(): Either<Failure, List<Article>>
}