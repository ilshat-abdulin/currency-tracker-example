package com.air.crypto.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.air.crypto.data.database.model.CoinInfoDbModel

@Database(entities = [CoinInfoDbModel::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "database.db"
    }

    abstract fun coinPriceInfoDao(): CoinPriceInfoDao
}

