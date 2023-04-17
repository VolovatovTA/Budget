package ru.bysoft.android.budget.features.create_update_wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.common.me_info.IMeInfo
import ru.bysoft.android.budget.common.util.BudgetCurrency
import ru.bysoft.android.budget.common.util.getCurrency
import ru.bysoft.android.budget.features.create_update_wallet.data.ICreateWalletRepository
import ru.bysoft.android.budget.features.create_update_wallet.data.entity.CreateWalletErrorData
import ru.bysoft.android.budget.features.create_update_wallet.navigation.ICreateWalletNavigation
import ru.bysoft.android.budget.features.create_update_wallet.presentation.entity.CreateWalletState
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState
import javax.inject.Inject

@HiltViewModel
class CreateWalletViewModel @Inject constructor(
    private val repository: ICreateWalletRepository,
    private val navigate: ICreateWalletNavigation,
    meInfo: IMeInfo,
    private val errorLogger: IErrorLogger
) : ViewModel() {

    private val createWalletExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorLogger.logError(throwable)
        _state.value = _state.value.copy(
            isLoading = false,
            toastText = "Произошла непредвиденная ошибка"
        )
    }

    private val _state = MutableStateFlow(
        CreateWalletState(
            currencyFieldState = CurrencyFieldState(
                selectedCurrency = getCurrency(meInfo.getCurrentMeInfo()!!.settingsData.currency)!!
            )
        )
    )
    val state: StateFlow<CreateWalletState>
        get() = _state


    fun onNameChanged(name: String) {
        _state.value = _state.value.copy(nameTextState = TextFieldState(name))
    }

    fun onBalanceChanged(balance: String) {
        _state.value = _state.value.copy(balanceTextState = TextFieldState(balance))
    }

    fun onCurrencySelected(currency: BudgetCurrency) {
        _state.value = _state.value.copy(
            currencyFieldState = _state.value.currencyFieldState.copy(
                selectedCurrency = currency
            )
        )
    }

    fun onButtonClick() {
        viewModelScope.launch(createWalletExceptionHandler) {
            _state.value = _state.value.copy(isLoading = true)
            val data = repository.createWallet(_state.value)
            if (data.errorType == null) {
                _state.value = _state.value.copy(isLoading = false)
                navigate.popBack()
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    balanceTextState = getBalanceStateByErrorType(data.errorType),
                    nameTextState = getNameStateByErrorType(data.errorType),
                    currencyFieldState = getCurrencyStateByErrorType(data.errorType),
                )
            }
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