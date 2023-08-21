package com.aeon.exchangeratesapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity(primaryKeys = ["base_currency", "comparable_currency"])
data class ExchangeRateValueUpdatingEntity(
    @ColumnInfo("base_currency")
    val baseCurrency: String,
    @ColumnInfo("comparable_currency")
    val comparableCurrency: String,
    @ColumnInfo("value")
    val value: Double,
) : Serializable