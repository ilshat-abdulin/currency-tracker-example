package com.air.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.air.core.database.model.CoinDbModel

@Database(entities = [CoinDbModel::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "database.db"
    }

    abstract fun coinDao(): CoinDao
}

