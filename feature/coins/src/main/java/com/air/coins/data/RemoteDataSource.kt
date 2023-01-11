package com.air.coins.data

import com.air.core.network.model.CoinInfoModel
import com.air.coins.domain.model.CoinHistory
import com.air.core_functional.Either
import com.air.core_functional.Failure

internal interface RemoteDataSource {
    suspend fun getTopCoinList(): Either<Failure, CoinInfoModel>
    suspend fun getCoinHistory(fSym: String): Either<Failure, CoinHistory>
}