package ru.bysoft.android.budget.common.navigation.settings

import androidx.navigation.NavHostController
import ru.bysoft.android.budget.common.navigation.auth.Auth
import ru.bysoft.android.budget.features.settings.presentation.navigation.ISettingsNavigation
import javax.inject.Inject

class SettingsNavigation @Inject constructor(
    private val navHostController: NavHostController
) : ISettingsNavigation {

    override fun toAuth() {
        navHostController.navigate(Auth.route) {
            popUpTo(Auth.route) {
                inclusive = true
            }
        }
    }

}