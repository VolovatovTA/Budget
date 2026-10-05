package ru.bysoft.android.budget.features.currency_rates.data.storage

import androidx.room.*
import ru.bysoft.android.budget.features.currency_rates.data.storage.entity.CurrencyRatesEntity

@Dao
interface ICurrencyRatesLocalStorage {
    @Query("SELECT * FROM CurrencyRatesEntity")
    fun getAllCurrencyRates(): List<CurrencyRatesEntity>

    @Query("SELECT * FROM CurrencyRatesEntity WHERE iso4217 = :isoCode")
    fun getCurrencyRateByIso(isoCode: String): CurrencyRatesEntity?

    @Insert
    fun insertCurrencyRate(user: CurrencyRatesEntity)

    @Update
    fun updateCurrencyRate(user: CurrencyRatesEntity)

}

@Database(entities = [CurrencyRatesEntity::class], version = 1)
abstract class CurrentRaceDatabase : RoomDatabase() {
    abstract fun currencyRatesDao(): ICurrencyRatesLocalStorage
}