package ru.bysoft.android.budget.features.currency_rates.data.storage

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.bysoft.android.budget.features.currency_rates.data.storage.entity.CurrencyRateEntity

@Dao
interface CurrencyRatesDao {
    @Query("SELECT * FROM currency_rate")
    suspend fun getAll(): List<CurrencyRateEntity>

    @Query("SELECT * FROM currency_rate WHERE base = :base AND target = :target")
    suspend fun get(base: String, target: String): CurrencyRateEntity?

    @Query("SELECT DISTINCT base FROM currency_rate")
    suspend fun bases(): List<String>

    @Query("SELECT MIN(updated_at) FROM currency_rate")
    suspend fun oldestUpdate(): Long?

    @Upsert
    suspend fun upsertAll(rates: List<CurrencyRateEntity>)
}
