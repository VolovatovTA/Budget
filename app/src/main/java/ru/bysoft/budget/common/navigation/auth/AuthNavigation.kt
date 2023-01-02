package ru.bysoft.budget.common.navigation.auth

import androidx.navigation.NavHostController
import ru.bysoft.budget.auth.navigation.IAuthNavigation
import com.example.bottom_navigation.navigation.BottomNavigation
import javax.inject.Inject

class AuthNavigation @Inject constructor(private val controller: NavHostController): IAuthNavigation {
    override fun toBottomNavigation() {
        controller.navigate(BottomNavigation.route)
    }
}