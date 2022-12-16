package ru.bysoft.budget.common.navigation.home

import androidx.navigation.NavHostController
import ru.bysoft.budget.features.bottom_navigation.home.navigation.IHomeNavigation
import com.example.bottom_navigation.navigation.create_wallet.CreateWalletNavigation
import javax.inject.Inject

class HomeNavigation @Inject constructor(
    private val navHostController: NavHostController
) : IHomeNavigation {

    override fun toCreateWallet() {
        navHostController.navigate(CreateWalletNavigation.route)
    }

}