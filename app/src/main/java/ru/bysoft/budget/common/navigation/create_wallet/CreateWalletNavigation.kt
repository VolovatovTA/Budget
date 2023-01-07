package ru.bysoft.budget.common.navigation.create_wallet

import androidx.navigation.NavHostController
import ru.bysoft.budget.features.create_update_wallet.navigation.ICreateWalletNavigation
import javax.inject.Inject

class CreateWalletNavigation @Inject constructor(
    private val navController: NavHostController
) : ICreateWalletNavigation {
    override fun popBack() {
        navController.popBackStack()
    }
}