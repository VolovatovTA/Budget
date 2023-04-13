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

interface ICreateWalletViewModel {
    val state: StateFlow<CreateWalletState>
    fun onNameChanged(name: String)
    fun onBalanceChanged(balance: String)
    fun onCurrencySelected(currency: BudgetCurrency)
    fun onButtonClick()
}

@HiltViewModel
class CreateWalletViewModel @Inject constructor(
    private val repository: ICreateWalletRepository,
    private val navigate: ICreateWalletNavigation,
    meInfo: IMeInfo,
    private val errorLogger: IErrorLogger
) : ViewModel(), ICreateWalletViewModel {

    private val createWalletExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorLogger.logError(throwable)
        state.value = state.value.copy(
            isLoading = false,
            toastText = "Произошла непредвиденная ошибка"
        )
    }

    override val state: MutableStateFlow<CreateWalletState> =
        MutableStateFlow(
            CreateWalletState(
                currencyFieldState = CurrencyFieldState(
                    selectedCurrency = getCurrency(meInfo.getCurrentMeInfo()!!.settingsData.currency)!!
                )
            )
        )

    override fun onNameChanged(name: String) {
        state.value = state.value.copy(nameTextState = TextFieldState(name))
    }

    override fun onBalanceChanged(balance: String) {
        state.value = state.value.copy(balanceTextState = TextFieldState(balance))
    }

    override fun onCurrencySelected(currency: BudgetCurrency) {
        state.value = state.value.copy(
            currencyFieldState = state.value.currencyFieldState.copy(
                selectedCurrency = currency
            )
        )
    }

    override fun onButtonClick() {
        viewModelScope.launch(createWalletExceptionHandler) {
            state.value = state.value.copy(isLoading = true)
            val data = repository.createWallet(state.value)
            if (data.errorType == null) {
                state.value = state.value.copy(isLoading = false)
                navigate.popBack()
            } else {
                state.value = state.value.copy(
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
            CreateWalletErrorData.INVALID_BALANCE -> state.value.balanceTextState.copy(
                errorText = CreateWalletErrorData.INVALID_BALANCE.errorText
            )
            else -> state.value.balanceTextState
        }

    private fun getNameStateByErrorType(errorType: CreateWalletErrorData): TextFieldState =
        when (errorType) {
            CreateWalletErrorData.INVALID_NAME -> state.value.nameTextState.copy(
                errorText = CreateWalletErrorData.INVALID_NAME.errorText
            )
            CreateWalletErrorData.NO_UNIQUE_NAME -> state.value.nameTextState.copy(
                errorText = CreateWalletErrorData.NO_UNIQUE_NAME.errorText
            )
            else -> state.value.nameTextState
        }

    private fun getCurrencyStateByErrorType(errorType: CreateWalletErrorData): CurrencyFieldState =
        when (errorType) {
            CreateWalletErrorData.INVALID_CURRENCY -> state.value.currencyFieldState.copy(
                errorText = CreateWalletErrorData.INVALID_CURRENCY.errorText
            )
            else -> state.value.currencyFieldState
        }
}