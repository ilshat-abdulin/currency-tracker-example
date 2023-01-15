package com.air.coins.data_source.remote

import com.air.core.network.model.coins.CoinInfoModel
import com.air.core.network.services.CoinsService
import com.air.core.network.executeRetrofitCall
import com.air.core_functional.map
import com.air.core_functional.suspendFlatMap
import com.air.coins.data.RemoteDataSource
import com.air.coins.data_source.mapper.CoinMapper
import com.air.coins.domain.model.CoinHistory
import com.air.core_functional.Either
import com.air.core_functional.Failure
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

internal class RemoteDataSourceImpl @Inject constructor(
    private val apiService: CoinsService,
    private val mapper: CoinMapper
) : RemoteDataSource {
    override suspend fun getTopCoinList(): Either<Failure, CoinInfoModel> {
        return executeRetrofitCall(Dispatchers.IO) {
            apiService.getTopCoinNames()
        }.map { names ->
            mapper.mapCoinNamesToMap(names)
        }.suspendFlatMap { namesMap ->
            executeRetrofitCall(Dispatchers.IO) {
                val fSyms = namesMap.keys.joinToString(",")
                apiService.getFullCoinList(fSyms = fSyms)
            }.map { jsonContainer ->
                val coinList = mapper.mapJsonContainerToCoinList(jsonContainer)
                CoinInfoModel(
                    coins = coinList,
                    coinNamesMap = namesMap
                )
            }
        }
    }

    override suspend fun getCoinHistory(fSym: String): Either<Failure, CoinHistory> {
        return executeRetrofitCall(Dispatchers.IO) {
            apiService.getCoinHistory(fSym).data?.data.orEmpty()
        }.map { response ->
            mapper.mapCoinHistoryDtoToEntity(response)
        }
    }
}
