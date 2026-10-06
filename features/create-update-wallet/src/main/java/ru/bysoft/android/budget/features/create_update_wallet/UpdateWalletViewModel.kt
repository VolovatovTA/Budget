package ru.bysoft.android.budget.features.create_update_wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.common.data_entity.CreateWalletErrorData
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.currency.getCurrency
import ru.bysoft.android.budget.features.create_update_wallet.data.IWalletRepository
import ru.bysoft.android.budget.features.create_update_wallet.navigation.IWalletNavigation
import ru.bysoft.android.budget.features.create_update_wallet.presentation.entity.ViewModelWalletState

class UpdateWalletViewModel(
    private val repository: IWalletRepository,
    private val navigate: IWalletNavigation,
    private val errorLogger: IErrorLogger,
    val walletScreenController: IWalletScreenController
) : ViewModel(), IUpdateWalletViewModel {

    private val createWalletExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorLogger.logError(throwable)
        _state.value = _state.value.copy(
            isLoading = false,
            toastText = "Произошла непредвиденная ошибка"
        )
    }

    lateinit var walletId: String

    private val _state = MutableStateFlow(
        ViewModelWalletState()
    )
    val state: StateFlow<ViewModelWalletState>
        get() = _state

    override fun onButtonClick() {
        viewModelScope.launch(createWalletExceptionHandler) {
            _state.value = _state.value.copy(isLoading = true)
            val result = repository.updateWallet(walletId = walletId, walletScreenController.state.value)
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

    override fun init(walletId: String?) {
        // повторный вызов после поворота экрана: данные уже загружены
        if (walletId == null || this::walletId.isInitialized) return
        this.walletId = walletId
        viewModelScope.launch(createWalletExceptionHandler) {
            _state.value = _state.value.copy(isLoading = true)
            val data = repository.getWalletData(walletId)
            _state.update {
                it.copy(
                    isLoading = false,
                )
            }
            walletScreenController.onBalanceChanged(data.balance.toString())
            walletScreenController.onIconSelected(data.iconName)
            walletScreenController.onNameChanged(data.name)
            val currency = getCurrency(data.currency)
            walletScreenController.onCurrencySelected(currency)
        }
    }

    override fun onWalletDeleteClick(walletId: String) {
        viewModelScope.launch(createWalletExceptionHandler) {
            _state.value = _state.value.copy(isLoading = true)
            repository.deleteWallet(walletId)
            navigate.popBack()
        }
    }

    override fun onBackClick() {
        navigate.popBack()
    }
}