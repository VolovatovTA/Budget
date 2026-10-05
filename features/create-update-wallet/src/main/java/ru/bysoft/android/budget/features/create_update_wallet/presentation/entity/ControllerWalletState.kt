package ru.bysoft.android.budget.features.create_update_wallet.presentation.entity

import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.icon_component.UiKitIconState
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState

data class ControllerWalletState(
    val nameTextState: TextFieldState = TextFieldState(""),
    val balanceTextState: TextFieldState = TextFieldState(""),
    val currencyFieldState: CurrencyFieldState,
    val iconState: UiKitIconState = UiKitIconState(null),
)

data class ViewModelWalletState(
    val isLoading: Boolean = false,
    val toastText: String? = null
)

