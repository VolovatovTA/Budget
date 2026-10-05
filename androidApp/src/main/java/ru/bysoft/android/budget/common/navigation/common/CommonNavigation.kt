package ru.bysoft.android.budget.common.navigation.common

import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import ru.bysoft.android.budget.common.navigation.NavHostControllerWrapper
import ru.bysoft.android.budget.common.navigation.auth.Auth
import ru.bysoft.android.budget.common.network.authentificator.ICommonNavigation

class CommonNavigation (
    private val navHostController: NavHostControllerWrapper
) : ICommonNavigation {
    override suspend fun navigateToAuth() {
        val id = navHostController.controller.filterNotNull().first().graph.id
        navHostController.navigate(Auth.route) {
            popUpTo(id) {
                inclusive = false
            }
        }
    }
}