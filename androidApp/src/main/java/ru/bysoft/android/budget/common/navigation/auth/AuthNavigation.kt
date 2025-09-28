package ru.bysoft.android.budget.common.navigation.auth

import androidx.navigation.NavHostController
import ru.bysoft.android.budget.auth.navigation.IAuthNavigation
import ru.bysoft.android.budget.common.navigation.NavHostControllerWrapper
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigation

class AuthNavigation (private val controller: NavHostControllerWrapper) :
    IAuthNavigation {
    override fun toBottomNavigation() {
        controller.navigate(BottomNavigation.route) {
            popUpTo(Auth.route) {
                inclusive = true
            }
        }
    }
}