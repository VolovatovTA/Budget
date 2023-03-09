package ru.bysoft.android.budget.common.navigation.splash

import androidx.navigation.NavHostController
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigation
import ru.bysoft.android.budget.auth.navigation.IAuthNavigation
import ru.bysoft.android.budget.common.navigation.auth.Auth
import ru.bysoft.android.budget.features.splash.navigation.ISplashNavigation
import javax.inject.Inject

class SplashNavigation @Inject constructor(private val controller: NavHostController) :
    ISplashNavigation {

    override fun toAuth() {
        controller.navigate(Auth.route)
    }

    override fun toBottomNavigation() {
        controller.navigate(BottomNavigation.route)
    }

}

class AuthNavigation @Inject constructor(private val controller: NavHostController) :
    IAuthNavigation {

    override fun toBottomNavigation() {
        controller.navigate(BottomNavigation.route)
    }

}