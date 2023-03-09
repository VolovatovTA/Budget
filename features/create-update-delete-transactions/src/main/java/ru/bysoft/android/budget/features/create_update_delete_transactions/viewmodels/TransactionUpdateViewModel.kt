package ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.ITransactionsCategoryApi
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.ITransactionsWalletApi
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.ITransactionNavigation
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionWalletPresentationMapper
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionsCategoryPresentationMapper
import javax.inject.Inject

@HiltViewModel
class TransactionUpdateViewModel @Inject constructor(
    navigate: ITransactionNavigation,
    errorLogger: IErrorLogger,
    categoryApi: ITransactionsCategoryApi,
    walletApi: ITransactionsWalletApi,
    categoryMapperPresentation: ITransactionsCategoryPresentationMapper,
    walletMapper: ITransactionWalletPresentationMapper,
) : TransactionsCommonViewModel(
    navigate = navigate,
    errorLogger = errorLogger,
    categoryApi = categoryApi,
    walletApi = walletApi,
    categoryMapperPresentation = categoryMapperPresentation,
    walletMapper = walletMapper
), ITransactionUpdateViewModel {

    override fun update() {
        viewModelScope.launch {

        }
    }

    override fun initId(id: String) {

    }
}