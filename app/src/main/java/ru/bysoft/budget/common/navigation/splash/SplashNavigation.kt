package ru.bysoft.budget.common.navigation.splash

import androidx.navigation.NavHostController
import com.example.bottom_navigation.navigation.BottomNavigation
import ru.bysoft.budget.auth.navigation.IAuthNavigation
import ru.bysoft.budget.common.navigation.auth.Auth
import ru.bysoft.budget.splash.navigation.ISplashNavigation
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