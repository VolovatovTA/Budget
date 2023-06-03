package ru.bysoft.android.budget.features.currency_rates.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.android.budget.features.currency_rates.data.CurrencyRatesRepo
import ru.bysoft.android.budget.features.currency_rates.data.ICurrencyRatesRepo

@Module
@InstallIn(SingletonComponent::class)
interface CurrencyRatedDi {
    @Binds
    fun bindRepo(repo: CurrencyRatesRepo): ICurrencyRatesRepo
}