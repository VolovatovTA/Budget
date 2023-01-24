package ru.bysoft.budget.create_update_delete_transactions.navigation.transaction

import androidx.navigation.NavHostController
import ru.bysoft.budget.create_update_delete_transactions.navigation.ITransactionNavigation
import javax.inject.Inject

class TransactionNavigation @Inject constructor(
    private val navHostController: NavHostController
) : ITransactionNavigation {

    override fun back() {
        navHostController.popBackStack()
    }
}