package com.air.core.network.services

import com.air.core.network.model.news.NewsResponse
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/news/")
    suspend fun getNews(
        @Query(QUERY_PARAM_LANG) lang: String = PARAM_LANG_EN,
    ): NewsResponse

    companion object {
        operator fun invoke(retrofit: Retrofit): NewsService = retrofit.create()

        private const val QUERY_PARAM_LANG = "lang"
        private const val PARAM_LANG_EN = "EN"
    }
}