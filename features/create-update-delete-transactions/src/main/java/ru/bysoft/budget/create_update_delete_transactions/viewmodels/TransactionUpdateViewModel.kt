package ru.bysoft.budget.create_update_delete_transactions.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.bysoft.budget.create_update_delete_transactions.navigation.ITransactionNavigation
import javax.inject.Inject

@HiltViewModel
class TransactionUpdateViewModel @Inject constructor(
    private val navigate: ITransactionNavigation
) : TransactionsCommonViewModel(navigate), ITransactionUpdateViewModel {
    override fun update() {
        viewModelScope.launch {

        }
    }

    override fun initId(id: String) {

    }
}