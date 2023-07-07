package ru.bysoft.android.budget.features.create_update_wallet.presentation.controllers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.bysoft.android.budget.common.data_entity.CreateWalletErrorData
import ru.bysoft.android.budget.common.me_info.IMeInfo
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.currency.getCurrency
import ru.bysoft.android.budget.features.create_update_wallet.IWalletScreenController
import ru.bysoft.android.budget.features.create_update_wallet.presentation.entity.ControllerWalletState
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState
import javax.inject.Inject

class WalletScreenController @Inject constructor(
    meInfo: IMeInfo
) : IWalletScreenController {

    private val _state = MutableStateFlow(
        ControllerWalletState(
            currencyFieldState = CurrencyFieldState(
                selectedCurrency = getCurrency(meInfo.getCurrentMeInfo()!!.settingsData?.currency)
            )
        )
    )
    override val state: StateFlow<ControllerWalletState>
        get() = _state

    override fun onNameChanged(name: String) {
        _state.update { it.copy(nameTextState = TextFieldState(name)) }
    }

    override fun onBalanceChanged(balance: String) {
        _state.update { it.copy(balanceTextState = TextFieldState(balance)) }
    }

    override fun onCurrencySelected(currency: BudgetCurrencyEnum) {
        _state.update {
            it.copy(
                currencyFieldState = it.currencyFieldState.copy(
                    selectedCurrency = currency
                )
            )
        }
    }

    override fun onIconSelected(iconName: String?) {
        _state.update {
            it.copy(
                iconState = it.iconState.copy(
                    iconName = iconName
                )
            )
        }
    }

    override fun showError(errorType: CreateWalletErrorData?) {
        errorType?.let {
            _state.value = _state.value.copy(
                balanceTextState = getBalanceStateByErrorType(errorType),
                nameTextState = getNameStateByErrorType(errorType),
                currencyFieldState = getCurrencyStateByErrorType(errorType),
            )
        }
    }

    private fun getBalanceStateByErrorType(errorType: CreateWalletErrorData): TextFieldState =
        when (errorType) {
            CreateWalletErrorData.INVALID_BALANCE -> _state.value.balanceTextState.copy(
                errorText = CreateWalletErrorData.INVALID_BALANCE.errorText
            )
            else -> _state.value.balanceTextState
        }

    private fun getNameStateByErrorType(errorType: CreateWalletErrorData): TextFieldState =
        when (errorType) {
            CreateWalletErrorData.INVALID_NAME -> _state.value.nameTextState.copy(
                errorText = CreateWalletErrorData.INVALID_NAME.errorText
            )
            CreateWalletErrorData.NO_UNIQUE_NAME -> _state.value.nameTextState.copy(
                errorText = CreateWalletErrorData.NO_UNIQUE_NAME.errorText
            )
            else -> _state.value.nameTextState
        }

    private fun getCurrencyStateByErrorType(errorType: CreateWalletErrorData): CurrencyFieldState =
        when (errorType) {
            CreateWalletErrorData.INVALID_CURRENCY -> _state.value.currencyFieldState.copy(
                errorText = CreateWalletErrorData.INVALID_CURRENCY.errorText
            )
            else -> _state.value.currencyFieldState
        }
}