package com.air.crypto.data

import com.air.core.network.model.CoinInfoModel
import com.air.crypto.domain.model.CoinHistory
import com.air.core_functional.Either
import com.air.core_functional.Failure

interface RemoteDataSource {
    suspend fun getTopCoinList(): Either<Failure, CoinInfoModel>
    suspend fun getCoinHistory(fSym: String): Either<Failure, CoinHistory>
}