package ru.bysoft.budget.common.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import ru.bysoft.budget.auth.presentation.screen.AuthScreen
import ru.bysoft.budget.auth.presentation.AuthViewModel
import ru.bysoft.budget.bottomnavigation.screen.BottomNavigationScreen
import ru.bysoft.budget.common.navigation.auth.Auth

@Composable
fun MainNavigationHost(navHostController: NavHostController = rememberNavController()) {
    NavHost(navController = navHostController, startDestination = Auth.route) {

        navigation(route = BottomNavigation.route, startDestination = BottomNavigation.screenName) {
            composable(BottomNavigation.screenName) { BottomNavigationScreen() }
        }

        navigation(route = Auth.route, startDestination = Auth.screenName) {
            composable(Auth.screenName) {
                AuthScreen(
                    navHostController,
                    hiltViewModel<AuthViewModel>()
                )
            }
        }

    }
}