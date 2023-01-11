package com.air.core.database

import androidx.room.*
import com.air.core.database.model.CoinDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM full_price_list")
    fun getCoinList(): Flow<List<CoinDbModel>>

    @Query("SELECT * FROM full_price_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getCoin(fSym: String): Flow<CoinDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoinList(coins: List<CoinDbModel>)

    @Query("DELETE FROM full_price_list")
    fun clearCoins()

    @Transaction
    suspend fun clearAndInsert(coins: List<CoinDbModel>) {
        clearCoins()
        insertCoinList(coins)
    }
}