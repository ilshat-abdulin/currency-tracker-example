package com.air.crypto.data

import com.air.crypto.data_source.remote.model.CoinInfoModel
import com.air.crypto.domain.model.CoinHistory
import com.air.crypto.util.Either
import com.air.crypto.util.Failure

interface RemoteDataSource {
    suspend fun getTopCoinList(): Either<Failure, CoinInfoModel>
    suspend fun getCoinHistory(fSym: String): Either<Failure, CoinHistory>
}