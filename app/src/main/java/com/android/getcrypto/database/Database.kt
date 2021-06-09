package com.android.getcrypto.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class Database : RoomDatabase() {

    companion object{
        private var db: Database? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): Database{
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                        context,
                        Database::class.java,
                        DB_NAME).build()
                db = instance
                return instance
            }
            }
        }
    }

