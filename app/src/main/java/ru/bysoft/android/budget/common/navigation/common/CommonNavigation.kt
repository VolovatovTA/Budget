package ru.bysoft.android.budget.common.navigation.common

import androidx.navigation.NavHostController
import ru.bysoft.android.budget.common.navigation.auth.Auth
import ru.bysoft.android.budget.common.network.authentificator.ICommonNavigation
import javax.inject.Inject

class CommonNavigation @Inject constructor(
    private val navHostController: NavHostController
) : ICommonNavigation {
    override fun navigateToAuth() {
        navHostController.navigate(Auth.route) {
            popUpTo(navHostController.graph.id) {
                inclusive = false
            }
        }
    }
}