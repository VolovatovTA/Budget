package ru.bysoft.android.budget.common.navigation.home

import androidx.navigation.NavHostController
import ru.bysoft.android.budget.features.bottom_navigation.home.navigation.IHomeNavigation
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.create_wallet.CreateWalletNavigation
import ru.bysoft.android.budget.common.util.toJson
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.TransactionUpdateNavParams
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.transaction.Transaction
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