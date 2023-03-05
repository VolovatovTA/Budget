package ru.bysoft.budget.common.navigation.home

import androidx.navigation.NavHostController
import ru.bysoft.budget.features.bottom_navigation.home.navigation.IHomeNavigation
import com.example.bottom_navigation.navigation.create_wallet.CreateWalletNavigation
import ru.bysoft.budget.common.util.toJson
import ru.bysoft.budget.create_update_delete_transactions.navigation.TransactionUpdateNavParams
import ru.bysoft.budget.create_update_delete_transactions.navigation.TransactionsCreateNavParams
import ru.bysoft.budget.create_update_delete_transactions.navigation.transaction.Transaction
import javax.inject.Inject

class HomeNavigation @Inject constructor(
    private val navHostController: NavHostController
) : IHomeNavigation {
    override fun toUpdateTransaction(id: String) {
        navHostController.navigate("${Transaction.updateScreen}/${TransactionUpdateNavParams(id).toJson()}")
    }

    override fun toCreateWallet() {
        navHostController.navigate(CreateWalletNavigation.route)
    }

    override fun toAuth() {
        navHostController.currentBackStackEntry?.destination?.route?.let {
            navHostController.navigate(
                route = it
            ) {
                popUpTo(it)
            }
        }
    }

}