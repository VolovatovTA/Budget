package ru.bysoft.android.budget.features.currency_rates.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.bysoft.android.budget.features.currency_rates.data.storage.entity.CurrencyRateEntity

/**
 * Cache of exchange rates. Version 2 replaced the one-row-per-base table that packed all
 * rates into a string with one row per (base, target) pair. It is a cache, so a schema
 * change just drops it (see the Koin module).
 */
@Database(entities = [CurrencyRateEntity::class], version = 2)
abstract class CurrencyRatesDatabase : RoomDatabase() {
    abstract fun currencyRatesDao(): CurrencyRatesDao
}
