package ru.bysoft.budget.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.bottom_navigation.navigation.BottomNavigation
import ru.bysoft.budget.auth.presentation.screen.AuthScreen
import com.example.bottom_navigation.screen.BottomNavigationScreen
import ru.bysoft.budget.common.navigation.auth.Auth
import ru.bysoft.budget.common.navigation.auth.AuthNavigation

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
                    AuthNavigation(navHostController)
                )
            }
        }

    }
}