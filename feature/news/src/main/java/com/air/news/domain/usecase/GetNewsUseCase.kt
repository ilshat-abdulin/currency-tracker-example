package com.air.news.domain.usecase

import com.air.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke() = newsRepository.getNews()
}