package ru.bysoft.android.budget.common.data_entity

import ru.bysoft.android.budget.common.util.BudgetCurrency

data class CurrencyRateData(
    val map: Map<BudgetCurrency, List<CurrencyRate>>
)

data class CurrencyRate(
    val currency: BudgetCurrency,
    val rate: Double
)