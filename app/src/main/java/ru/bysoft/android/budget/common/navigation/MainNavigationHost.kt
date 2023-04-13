package ru.bysoft.android.budget.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigation
import ru.bysoft.android.budget.auth.presentation.screen.AuthScreen
import ru.bysoft.android.budget.features.bottom_navigation.host.screen.BottomNavigationScreen
import ru.bysoft.android.budget.common.navigation.auth.Auth
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.create_wallet.CreateWalletNavigation
import ru.bysoft.android.budget.common.navigation.create_update_categiry.CreateUpdateCategory
import ru.bysoft.android.budget.common.navigation.splash.Splash
import ru.bysoft.android.budget.common.util.restore
import ru.bysoft.android.budget.features.create_udate_category.navigation.CreateCategoryNavInfo
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.transaction.Transaction
import ru.bysoft.android.budget.features.create_udate_category.presentation.screen.CreateCategoryScreen
import ru.bysoft.android.budget.features.create_udate_category.presentation.screen.UpdateCategoryScreen
import ru.bysoft.android.budget.features.create_udate_category.presentation.viewmodels.CreateCategoryViewModel
import ru.bysoft.android.budget.features.create_udate_category.presentation.viewmodels.UpdateCategoryViewModel
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.TransactionScreen
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.TransactionCreateViewModel
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.TransactionUpdateViewModel
import ru.bysoft.android.budget.features.create_update_wallet.presentation.screen.CreateWalletScreen
import ru.bysoft.android.budget.features.splash.presentation.screen.SplashScreen

@Composable
fun MainNavigationHost(mainNavController: NavHostController) {
    NavHost(navController = mainNavController, startDestination = Splash.route) {

        navigation(route = Splash.route, startDestination = Splash.screenName) {
            composable(Splash.screenName) {
                SplashScreen()
            }
        }

        navigation(route = BottomNavigation.route, startDestination = BottomNavigation.screenName) {
            composable(BottomNavigation.screenName) {
                val bottomNavigationController = rememberNavController()
                BottomNavigationScreen(
                    bottomNavigateionNavController = bottomNavigationController,
                    mainNavController = mainNavController
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
            composable("${CreateUpdateCategory.createScreenName}/{arguments}") {
                val typeCategory = it.arguments?.getString("arguments")?.restore<CreateCategoryNavInfo>()
                val viewModel = hiltViewModel<CreateCategoryViewModel>()
                viewModel.initNavParams(typeCategory)
                CreateCategoryScreen(viewModel)
            }
            composable("${CreateUpdateCategory.updateDeleteScreenName}/{arguments}") {
                val id = it.arguments?.getString("arguments")!!
                UpdateCategoryScreen(hiltViewModel<UpdateCategoryViewModel>(), id)
            }
        }

        navigation(
            route = Transaction.route,
            startDestination = Transaction.screenName
        ) {
            val argumentName = "argument"

            composable(
                route = "${Transaction.createScreen}/{$argumentName}",
                arguments = listOf(navArgument(argumentName) { type = NavType.StringType })

            ) {
//                 = ""
//                val transactionsNavParams = it.arguments?.getString("argument")?.restore<TransactionsNavParams>()!!
                val viewModel = hiltViewModel<TransactionCreateViewModel>()
//                LaunchedEffect(Unit) { viewModel.initNavParams(transactionsNavParams) }
                TransactionScreen(viewModel)
            }

            composable("${Transaction.updateScreen}/{arguments}") {
                val id = it.arguments?.getString("arguments")!!
                val viewModel = hiltViewModel<TransactionUpdateViewModel>()
                LaunchedEffect(Unit){ viewModel.initId(id) }
                TransactionScreen(viewModel)
            }
        }
    }
}