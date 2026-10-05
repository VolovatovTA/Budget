package ru.bysoft.android.budget.uikit.components.currencyfield.entity

import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.currency.getAvailableCurrency

data class CurrencyFieldState(
    val selectedCurrency: BudgetCurrencyEnum?,
    val list: List<BudgetCurrencyEnum> = getAvailableCurrency(),
    val errorText: Int? = null
)

data class PopupFieldState <T> (
    val selectedValue: T?,
    val list: List<T> = emptyList(),
    val errorText: String? = null
)
