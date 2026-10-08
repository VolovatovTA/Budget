package ru.bysoft.android.budget.features.currency_rates.data.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

/** One exchange rate: 1 [base] = [rate] [target]. Both codes are ISO 4217. */
@Entity(tableName = "currency_rate", primaryKeys = ["base", "target"])
data class CurrencyRateEntity(
    val base: String,
    val target: String,
    val rate: Double,
    /** Epoch millis of the sync that wrote this row. */
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)
