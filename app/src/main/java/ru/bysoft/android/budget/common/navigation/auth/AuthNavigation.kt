package ru.bysoft.android.budget.common.navigation.auth

import androidx.navigation.NavHostController
import ru.bysoft.android.budget.auth.navigation.IAuthNavigation
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigation
import javax.inject.Inject

class AuthNavigation @Inject constructor(private val controller: NavHostController):
    IAuthNavigation {
    override fun toBottomNavigation() {
        controller.navigate(BottomNavigation.route)
    }
}