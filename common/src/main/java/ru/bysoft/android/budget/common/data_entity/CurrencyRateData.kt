package ru.bysoft.android.budget.common.data_entity

import ru.bysoft.android.budget.currency.BudgetCurrencyEnum

data class CurrencyRateData(
    val map: Map<BudgetCurrencyEnum, List<CurrencyRate>>
)

data class CurrencyRate(
    val currency: BudgetCurrencyEnum,
    val rate: Double
)