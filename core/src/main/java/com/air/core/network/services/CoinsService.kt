package com.air.core.network.services

import com.air.core.network.model.CoinHistoryResponse
import com.air.core.network.model.CoinJsonContainerDto
import com.air.core.network.model.CoinNamesListDto
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinsService {

    @GET("top/totalvolfull")
    suspend fun getTopCoinNames(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY_USD
    ): CoinNamesListDto

    @GET("pricemultifull")
    suspend fun getFullCoinList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY_USD
    ): CoinJsonContainerDto

    @GET("v2/histohour")
    suspend fun getCoinHistory(
        @Query(QUERY_PARAM_FROM_SYMBOL) fSym: String,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY_USD,
        @Query(QUERY_PARAM_LIMIT) limit: Int = 48
    ): CoinHistoryResponse

    companion object {
        operator fun invoke(retrofit: Retrofit): CoinsService = retrofit.create()

        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val QUERY_PARAM_FROM_SYMBOL = "fsym"
        private const val CURRENCY_USD = "USD"
        private const val CURRENCY_EUR = "EUR"
        private const val CURRENCY_RUR = "RUR"
    }
}