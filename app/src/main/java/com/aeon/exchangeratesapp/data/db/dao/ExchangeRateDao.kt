package com.aeon.exchangeratesapp.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.aeon.exchangeratesapp.data.db.entity.ExchangeRateEntity
import com.aeon.exchangeratesapp.data.db.entity.ExchangeRateValueUpdatingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeRateDao {

    @Upsert
    suspend fun createOrUpdateExchangeRate(exchangeRateEntity: ExchangeRateEntity)

    @Update(entity = ExchangeRateEntity::class)
    suspend fun updateExchangeRates(exchangeRateEntityList: List<ExchangeRateValueUpdatingEntity>)

    @Query("UPDATE exchange_rate SET isFavourite = 0 " +
            "WHERE base_currency == :baseCurrency AND comparable_currency == :comparableCurrency")
    suspend fun unmakeFavourite(baseCurrency: String, comparableCurrency: String)

    @Query("SELECT * FROM exchange_rate WHERE base_currency == :baseCurrency")
    fun observeAllFavourites(baseCurrency: String): Flow<List<ExchangeRateEntity>>

}
