package ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.ICategoriesRepo
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.IWalletsRepo
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.ITransactionNavigation
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionWalletPresentationMapper
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionsCategoryPresentationMapper

class TransactionUpdateViewModel(
    navigate: ITransactionNavigation,
    errorLogger: IErrorLogger,
    categoriesRepo: ICategoriesRepo,
    walletsRepo: IWalletsRepo,
    categoryMapperPresentation: ITransactionsCategoryPresentationMapper,
    walletMapper: ITransactionWalletPresentationMapper,
) : TransactionsCommonViewModel(
    navigate = navigate,
    errorLogger = errorLogger,
    categoriesRepo = categoriesRepo,
    walletsRepo = walletsRepo,
    categoryMapperPresentation = categoryMapperPresentation,
    walletMapper = walletMapper
), ITransactionUpdateViewModel {

    override fun update() {
        // TODO: updating a transaction is not implemented yet
    }

    override fun initId(id: String) {

    }
}