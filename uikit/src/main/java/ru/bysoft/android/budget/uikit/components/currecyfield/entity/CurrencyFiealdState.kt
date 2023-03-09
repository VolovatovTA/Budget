package ru.bysoft.android.budget.uikit.components.currecyfield.entity

import ru.bysoft.android.budget.common.util.BudgetCurrency
import ru.bysoft.android.budget.common.util.getAvailableCurrency

data class CurrencyFieldState(
    val selectedCurrency: BudgetCurrency?,
    val list: List<BudgetCurrency> = getAvailableCurrency(),
    val errorText: Int? = null
)

data class PopupFieldState <T> (
    val selectedValue: T?,
    val list: List<T> = emptyList(),
    val errorText: String? = null
)
