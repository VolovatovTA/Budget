package ru.bysoft.android.budget.common.di

import android.content.Context
import ru.bysoft.android.budget.common.BudgetApplication
import ru.bysoft.android.budget.features.currency_rates.data.storage.ICurrencyRatesLocalStorage

class DatabaseModule {
    fun provideStorage(
        context: Context
    ): ICurrencyRatesLocalStorage =
        (context as BudgetApplication).database.currencyRatesDao()
}