package com.air.crypto.data.network

import com.air.crypto.data.network.model.CoinHistoryResponse
import com.air.crypto.data.network.model.CoinInfoJsonContainerDto
import com.air.crypto.data.network.model.CoinNamesListDto
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    suspend fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY_USD
    ): CoinNamesListDto

    @GET("pricemultifull")
    suspend fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY_USD
    ): CoinInfoJsonContainerDto

    @GET("v2/histohour")
    suspend fun getCoinHistory(
        @Query(QUERY_PARAM_FROM_SYMBOL) fSym: String,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY_USD,
        @Query(QUERY_PARAM_LIMIT) limit: Int = 48
    ): CoinHistoryResponse

    companion object {
        operator fun invoke(retrofit: Retrofit): ApiService = retrofit.create()

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