package ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.budget.android.api.data.source.network.ICategoryApi
import ru.budget.android.api.data.source.network.IWalletApi
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.ITransactionNavigation
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionWalletPresentationMapper
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionsCategoryPresentationMapper
import javax.inject.Inject

@HiltViewModel
class TransactionUpdateViewModel @Inject constructor(
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
        viewModelScope.launch {

        }
    }

    override fun initId(id: String) {

    }
}