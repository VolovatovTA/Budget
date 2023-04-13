package ru.bysoft.android.budget.features.create_update_wallet.presentation.entity

import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState

data class CreateWalletState(
    val nameTextState: TextFieldState = TextFieldState(""),
    val balanceTextState: TextFieldState = TextFieldState("0.0"),
    val currencyFieldState: CurrencyFieldState,
    val isLoading: Boolean = false,
    val toastText: String? = null
)

