package ru.bysoft.android.budget.uikit.components.currencyfield.entity

/** Dropdown state for a currency-like selector. [T] is whatever the feature uses as a currency. */
data class CurrencyFieldState<T>(
    val selectedCurrency: T?,
    val list: List<T> = emptyList(),
    val errorText: Int? = null
)

data class PopupFieldState<T>(
    val selectedValue: T?,
    val list: List<T> = emptyList(),
    val errorText: String? = null
)
