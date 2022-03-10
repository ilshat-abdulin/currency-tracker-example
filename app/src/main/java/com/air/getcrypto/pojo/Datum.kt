package com.air.getcrypto.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Datum(
        //объект CoinInfo с данными о криптовалюте
        @SerializedName("CoinInfo")
        @Expose
        val coinInfo: CoinInfo? = null
)