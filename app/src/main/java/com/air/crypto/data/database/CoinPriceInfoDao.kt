package com.air.crypto.data.database

import androidx.room.*
import com.air.crypto.data.database.model.CoinInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinPriceInfoDao {
    @Query("SELECT * FROM full_price_list")
    fun getPriceList(): Flow<List<CoinInfoDbModel>>

    @Query("SELECT * FROM full_price_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getPriceInfoAboutCoin(fSym: String): Flow<CoinInfoDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(priceList: List<CoinInfoDbModel>)

    @Query("DELETE FROM full_price_list")
    fun clearPriceList()

    @Transaction
    suspend fun clearAndInsert(priceList: List<CoinInfoDbModel>) {
        clearPriceList()
        insertPriceList(priceList)
    }
}