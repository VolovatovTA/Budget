package ru.bysoft.budget.features.create_wallet.presentation.entity

import ru.bysoft.budget.common.util.BudgetCurrency
import ru.bysoft.budget.common.util.getAvailableCurrency
import ru.bysoft.budget.uikit.components.textfield.TextFieldState

data class CreateWalletState(
    val nameTextState: TextFieldState = TextFieldState(""),
    val balanceTextState: TextFieldState = TextFieldState("0.0"),
    val currencyFieldState: CurrencyFieldState,
    val isLoading: Boolean = false,
    val toastText: String? = null
)

data class CurrencyFieldState(
    val selectedCurrency: BudgetCurrency,
    val list: List<BudgetCurrency> = getAvailableCurrency(),
    val errorText: String? = null
)

