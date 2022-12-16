package ru.bysoft.budget.features.create_wallet.presentation.entity

import ru.bysoft.budget.common.util.BudgetCurrency
import ru.bysoft.budget.common.util.getAvailableCurrency
import ru.bysoft.budget.uikit.components.textfield.TextFieldState

data class CreateWalletState(
    val nameTextState: TextFieldState = TextFieldState(""),
    val balanceTextState: TextFieldState = TextFieldState("0.0"),
    val currencyFieldState: CurrencyField,
    val isLoading: Boolean = false
)

data class CurrencyField(
    val selectedCurrency: BudgetCurrency,
    val list: List<BudgetCurrency> = getAvailableCurrency()
)

