package com.air.crypto.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNameDto(
    @SerializedName("Name")
    @Expose
    val name: String? = null,
    @SerializedName("FullName")
    @Expose
    val fullName: String? = null,
)