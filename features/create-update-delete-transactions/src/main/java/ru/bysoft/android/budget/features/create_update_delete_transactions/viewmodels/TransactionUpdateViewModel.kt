package ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.budget.android.api.data.source.network.ICategoryApi
import ru.budget.android.api.data.source.network.IWalletApi
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.ITransactionNavigation
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionWalletPresentationMapper
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionsCategoryPresentationMapper

class TransactionUpdateViewModel(
    navigate: ITransactionNavigation,
    errorLogger: IErrorLogger,
    categoryApi: ICategoryApi,
    walletApi: IWalletApi,
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
        // TODO: updating a transaction is not implemented yet
    }

    override fun initId(id: String) {

    }
}