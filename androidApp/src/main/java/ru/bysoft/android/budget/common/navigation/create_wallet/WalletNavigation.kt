package ru.bysoft.android.budget.common.navigation.create_wallet

import ru.bysoft.android.budget.common.navigation.NavHostControllerWrapper
import ru.bysoft.android.budget.features.create_update_wallet.navigation.IWalletNavigation

class WalletNavigation(
    private val navController: NavHostControllerWrapper
) : IWalletNavigation {
    override fun popBack() {
        navController.popBackStack()
    }
}