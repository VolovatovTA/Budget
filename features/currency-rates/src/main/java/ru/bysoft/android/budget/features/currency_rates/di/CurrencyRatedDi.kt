package ru.bysoft.android.budget.features.currency_rates.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.budget.android.api.data.mapper.CurrencyRatesDataMapper
import ru.bysoft.android.budget.features.currency_rates.data.CurrencyRatesRepo
import ru.bysoft.android.budget.features.currency_rates.data.ICurrencyRatesRepo
import ru.bysoft.android.budget.features.currency_rates.data.storage.CurrencyRatesDao
import ru.bysoft.android.budget.features.currency_rates.data.storage.CurrencyRatesDatabase

val CurrencyRatedDi = module {
    singleOf(::CurrencyRatesDataMapper)
    single { CurrencyRatesRepo(api = get(), mapper = get(), dao = get()) } bind ICurrencyRatesRepo::class

    single<CurrencyRatesDatabase> {
        Room.databaseBuilder(androidContext(), CurrencyRatesDatabase::class.java, "currency_rates.db")
            // the table is a cache that refills itself; on a schema change just start over
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    single<CurrencyRatesDao> { get<CurrencyRatesDatabase>().currencyRatesDao() }
}
