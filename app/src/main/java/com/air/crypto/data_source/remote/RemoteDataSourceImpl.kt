package com.air.crypto.data_source.remote

import com.air.crypto.data.RemoteDataSource
import com.air.crypto.data_source.mapper.CoinMapper
import com.air.crypto.data_source.remote.model.CoinInfoModel
import com.air.crypto.data_source.remote.network.ApiService
import com.air.crypto.domain.model.CoinHistory
import com.air.crypto.util.*
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
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
