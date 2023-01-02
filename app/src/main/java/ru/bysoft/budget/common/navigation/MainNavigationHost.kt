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
import com.example.bottom_navigation.navigation.create_wallet.CreateWalletNavigation
import ru.bysoft.budget.common.navigation.home.HomeNavigation
import ru.bysoft.budget.common.navigation.splash.Splash
import ru.bysoft.budget.features.create_wallet.presentation.screen.CreateWalletScreen
import ru.bysoft.budget.splash.presentation.screen.SplashScreen

@Composable
fun MainNavigationHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Splash.route) {

        navigation(route = Splash.route, startDestination = Splash.screenName) {
            composable(Splash.screenName) {
                SplashScreen()
            }
        }

        navigation(route = BottomNavigation.route, startDestination = BottomNavigation.screenName) {
            composable(BottomNavigation.screenName) {
                val bottomNavigationController = rememberNavController()
                BottomNavigationScreen(
                    bottomNavigationController
                )
            }
        }

        navigation(route = Auth.route, startDestination = Auth.screenName) {
            composable(Auth.screenName) {
                AuthScreen()
            }
        }

        navigation(
            route = CreateWalletNavigation.route,
            startDestination = CreateWalletNavigation.screenName
        ) {
            composable(CreateWalletNavigation.screenName) {
                CreateWalletScreen()
            }
        }

    }
}