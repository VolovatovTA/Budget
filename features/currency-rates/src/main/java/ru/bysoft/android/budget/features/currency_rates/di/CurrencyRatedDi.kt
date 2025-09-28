package ru.bysoft.android.budget.features.currency_rates.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.budget.android.api.data.mapper.CurrencyRatesDataMapper
import ru.bysoft.android.budget.features.currency_rates.data.CurrencyRatesRepo
import ru.bysoft.android.budget.features.currency_rates.data.ICurrencyRatesRepo
import ru.bysoft.android.budget.features.currency_rates.data.storage.CurrentRaceDatabase
import ru.bysoft.android.budget.features.currency_rates.data.storage.ICurrencyRatesLocalStorage

val CurrencyRatedDi = module {
    singleOf(::CurrencyRatesDataMapper)
    singleOf(::CurrencyRatesRepo) bind ICurrencyRatesRepo::class

    single<CurrentRaceDatabase> {
        Room.databaseBuilder(
            androidContext(),
            CurrentRaceDatabase::class.java,
            "currency_rates.db"
        ).build()
    }

    single<ICurrencyRatesLocalStorage> { get<CurrentRaceDatabase>().currencyRatesDao() }
}