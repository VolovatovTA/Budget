package ru.bysoft.budget.common

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import java.util.Currency

@HiltAndroidApp
class BudgetApplication : Application() {
    init {
        val d = Currency.getAvailableCurrencies()
    }
}