package ru.bysoft.android.budget.common.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.bysoft.android.budget.common.BudgetApplication
import ru.bysoft.android.budget.features.currency_rates.data.storage.ICurrencyRatesLocalStorage

@Module
@InstallIn(SingletonComponent::class)
class DatabaseDi {
    @Provides
    fun provideStorage(
        @ApplicationContext context: Context
    ): ICurrencyRatesLocalStorage =
        (context as BudgetApplication).database.currencyRatesDao()
}