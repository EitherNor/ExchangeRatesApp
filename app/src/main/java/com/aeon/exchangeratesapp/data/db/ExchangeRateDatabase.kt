package com.aeon.exchangeratesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aeon.exchangeratesapp.data.db.dao.ExchangeRateDao
import com.aeon.exchangeratesapp.data.db.entity.ExchangeRateEntity

@Database(entities = [ExchangeRateEntity::class], exportSchema = false, version = 1)
abstract class ExchangeRateDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "exchange_rate_db"
        private var instance: ExchangeRateDatabase? = null

        fun getInstance(context: Context): ExchangeRateDatabase {
            return instance ?: Room.databaseBuilder(
                context.applicationContext,
                ExchangeRateDatabase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun exchangeRateDao(): ExchangeRateDao
}
