package ru.bysoft.budget.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.bysoft.budget.auth.screen.AuthScreen
import ru.bysoft.budget.common.navigation.auth.Auth

@Composable
fun MainNavigationHost() {
    NavHost(navController = rememberNavController(), startDestination = Auth.route) {

        composable(Auth.route) { AuthScreen() }
        composable(BottomNavigation.route) { BottomNavigationNavHost() }

    }
}