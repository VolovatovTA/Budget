package ru.bysoft.android.budget.features.create_update_wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.currency.BudgetCurrency
import ru.bysoft.android.budget.features.create_update_wallet.data.IWalletRepository
import ru.bysoft.android.budget.common.data_entity.CreateWalletErrorData
import ru.bysoft.android.budget.features.create_update_wallet.navigation.IWalletNavigation
import ru.bysoft.android.budget.features.create_update_wallet.presentation.entity.ControllerWalletState
import ru.bysoft.android.budget.features.create_update_wallet.presentation.entity.ViewModelWalletState
import javax.inject.Inject

interface IWalletScreenController {
    val state: StateFlow<ControllerWalletState>

    fun onNameChanged(name: String)
    fun onBalanceChanged(balance: String)
    fun onCurrencySelected(currency: BudgetCurrency)
    fun onIconSelected(iconName: String?)
    fun showError(errorType: CreateWalletErrorData?)
}

sealed interface IWalletViewModel {
    fun onButtonClick()

    fun onBackClick()
}

interface ICreateWalletViewModel : IWalletViewModel

interface IUpdateWalletViewModel : IWalletViewModel {
    fun init(walletId: String?)
    fun onWalletDeleteClick(walletId: String)
}

@HiltViewModel
class CreateWalletViewModel @Inject constructor(
    private val repository: IWalletRepository,
    private val navigate: IWalletNavigation,
    private val errorLogger: IErrorLogger,
    val walletScreenController: IWalletScreenController
) : ViewModel(), ICreateWalletViewModel {

    private val createWalletExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorLogger.logError(throwable)
        _state.value = _state.value.copy(
            isLoading = false,
            toastText = "Произошла непредвиденная ошибка"
        )
    }

    private val _state = MutableStateFlow(
        ViewModelWalletState()
    )
    val state: StateFlow<ViewModelWalletState>
        get() = _state

    override fun onButtonClick() {
        viewModelScope.launch(createWalletExceptionHandler) {
            _state.value = _state.value.copy(isLoading = true)
            val result = repository.createWallet(walletScreenController.state.value)
            if (result.isSuccess) {
                _state.value = _state.value.copy(isLoading = false)
                navigate.popBack()
            } else {
                walletScreenController.showError(result.exceptionOrNull() as? CreateWalletErrorData)
                _state.value = _state.value.copy(
                    isLoading = false
                )
            }
        }
    }

    override fun onBackClick() {
        navigate.popBack()
    }
}