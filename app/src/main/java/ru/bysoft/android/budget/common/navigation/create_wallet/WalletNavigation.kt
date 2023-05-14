package ru.bysoft.android.budget.common.navigation.create_wallet

import androidx.navigation.NavHostController
import ru.bysoft.android.budget.features.create_update_wallet.navigation.IWalletNavigation
import javax.inject.Inject

class WalletNavigation @Inject constructor(
    private val navController: NavHostController
) : IWalletNavigation {
    override fun popBack() {
        navController.popBackStack()
    }
}