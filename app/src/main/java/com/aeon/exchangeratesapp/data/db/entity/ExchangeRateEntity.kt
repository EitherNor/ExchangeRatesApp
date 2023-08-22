package com.aeon.exchangeratesapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity("exchange_rate", primaryKeys = ["base_currency", "comparable_currency"])
data class ExchangeRateEntity(
        @ColumnInfo("base_currency")
        val baseCurrency: String,
        @ColumnInfo("comparable_currency")
        val comparableCurrency: String,
        @ColumnInfo("value")
        val value: Double,
        @ColumnInfo("isFavourite")
        val isFavourite: Boolean,
) : Serializable
