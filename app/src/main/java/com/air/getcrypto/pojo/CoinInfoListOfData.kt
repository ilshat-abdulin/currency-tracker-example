package com.air.getcrypto.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoListOfData(
        //запрос спика популярных валют
        @SerializedName("Data")
        @Expose
        val data: List<Datum>? = null
)