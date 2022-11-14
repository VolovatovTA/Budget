package ru.bysoft.budget.common.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.bysoft.budget.auth.presentation.AuthScreen
import ru.bysoft.budget.auth.presentation.AuthViewModel
import ru.bysoft.budget.common.navigation.auth.Auth

@Composable
fun MainNavigationHost() {
    NavHost(navController = rememberNavController(), startDestination = Auth.route) {

        composable(Auth.route) { AuthScreen(hiltViewModel<AuthViewModel>()) }
        composable(BottomNavigation.route) { BottomNavigationNavHost() }

    }
}