package ru.bysoft.android.budget.common.navigation.transaction_detail

import ru.bysoft.android.budget.common.navigation.NavHostControllerWrapper
import ru.bysoft.android.budget.features.transaction_detail.navigation.ITransactionDetailNavigation

class TransactionDetailNavigation(
    private val navHostController: NavHostControllerWrapper
) : ITransactionDetailNavigation {
    override fun back() = navHostController.popBackStack()
}
