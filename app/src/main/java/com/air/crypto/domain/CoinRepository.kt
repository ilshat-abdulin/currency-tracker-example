package com.air.crypto.domain

import androidx.lifecycle.LiveData
import com.air.crypto.domain.model.CoinInfo
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoinInfoList(): Flow<List<CoinInfo>>

    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>

    fun loadData()
}