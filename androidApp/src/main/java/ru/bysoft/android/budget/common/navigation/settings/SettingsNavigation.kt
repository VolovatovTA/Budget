package ru.bysoft.android.budget.common.navigation.settings

import ru.bysoft.android.budget.common.navigation.NavHostControllerWrapper
import ru.bysoft.android.budget.common.navigation.auth.Auth
import ru.bysoft.android.budget.features.settings.presentation.navigation.ISettingsNavigation

class SettingsNavigation(
    private val navHostController: NavHostControllerWrapper
) : ISettingsNavigation {

    override fun toAuth() {
        navHostController.navigate(Auth.route) {
            popUpTo(Auth.route) {
                inclusive = true
            }
        }
    }

    override fun popBack() {
        navHostController.popBackStack()
    }

}