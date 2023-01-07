package ru.bysoft.budget.common.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.bottom_navigation.navigation.BottomNavigation
import ru.bysoft.budget.auth.presentation.screen.AuthScreen
import com.example.bottom_navigation.screen.BottomNavigationScreen
import ru.bysoft.budget.common.navigation.auth.Auth
import com.example.bottom_navigation.navigation.create_wallet.CreateWalletNavigation
import ru.bysoft.budget.common.navigation.create_update_categiry.CreateUpdateCategory
import ru.bysoft.budget.common.navigation.splash.Splash
import ru.bysoft.budget.create_udate_category.presentation.screen.CreateCategoryScreen
import ru.bysoft.budget.create_udate_category.presentation.screen.UpdateCategoryScreen
import ru.bysoft.budget.create_udate_category.presentation.viewmodels.CreateCategoryViewModel
import ru.bysoft.budget.create_udate_category.presentation.viewmodels.UpdateCategoryViewModel
import ru.bysoft.budget.features.create_update_wallet.presentation.screen.CreateWalletScreen
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

        navigation(
            route = CreateUpdateCategory.route,
            startDestination = CreateUpdateCategory.screenName
        ) {
            composable(CreateUpdateCategory.createScreenName) {
                CreateCategoryScreen(hiltViewModel<CreateCategoryViewModel>())
            }
            composable("${CreateUpdateCategory.updateDeleteScreenName}/{arguments}") {
                val id = it.arguments?.getString("arguments")!!
                UpdateCategoryScreen(hiltViewModel<UpdateCategoryViewModel>(), id)
            }
        }
    }
}