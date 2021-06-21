package com.android.getcrypto.api

import com.android.getcrypto.pojo.CoinInfoListOfData
import com.android.getcrypto.pojo.CoinPriceInfoRawData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //получаем список самых популярных валют

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
            @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
            @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
            @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY_USD
    ): Single<CoinInfoListOfData>

    @GET("pricemultifull")
    fun getFullPriceList(
            @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
            @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
            @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY_USD

    ): Single<CoinPriceInfoRawData>

    companion object {
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val CURRENCY_USD = "USD"
        private const val CURRENCY_EUR = "EUR"
        private const val CURRENCY_RUR = "RUR"
    }

}