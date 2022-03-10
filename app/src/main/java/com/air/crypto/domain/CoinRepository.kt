package com.air.crypto.domain

import androidx.lifecycle.LiveData
import com.air.crypto.domain.model.CoinInfo

interface CoinRepository {
    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>

    fun loadData()
}