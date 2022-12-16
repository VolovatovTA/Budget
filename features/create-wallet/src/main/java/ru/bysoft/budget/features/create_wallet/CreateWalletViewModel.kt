package ru.bysoft.budget.features.create_wallet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.bysoft.budget.common.errors.exceptionHandler
import ru.bysoft.budget.common.me_info.IMeInfo
import ru.bysoft.budget.common.util.BudgetCurrency
import ru.bysoft.budget.common.util.getCurrency
import ru.bysoft.budget.features.create_wallet.data.ICreateWalletRepository
import ru.bysoft.budget.features.create_wallet.navigation.ICreateWalletNavigation
import ru.bysoft.budget.features.create_wallet.presentation.entity.CreateWalletState
import ru.bysoft.budget.features.create_wallet.presentation.entity.CurrencyField
import ru.bysoft.budget.uikit.components.textfield.TextFieldState
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
    private val meInfo: IMeInfo
) : ViewModel(), ICreateWalletViewModel {
    val TAG = "Timofey"
    init {
        val d = meInfo.getCurrentMeInfo()
        Log.d(TAG, d.toString())
    }
    override val state: MutableStateFlow<CreateWalletState> =
        MutableStateFlow(
            CreateWalletState(
                currencyFieldState = CurrencyField(
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
        viewModelScope.launch(exceptionHandler) {
            state.value = state.value.copy(isLoading = true)
            repository.createWallet(state.value)
            navigate.popBack()
            state.value = state.value.copy(isLoading = false)
        }
    }

}