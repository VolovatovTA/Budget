package ru.bysoft.android.budget.features.currency_rates.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRatesEntity(
    @PrimaryKey val iso4217: String,
    val list: String
)
