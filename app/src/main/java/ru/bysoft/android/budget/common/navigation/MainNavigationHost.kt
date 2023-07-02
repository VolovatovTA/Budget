package ru.bysoft.android.budget.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.create_wallet.WalletNavigation
import ru.bysoft.android.budget.common.navigation.create_update_categiry.CreateUpdateCategory
import ru.bysoft.android.budget.common.navigation.splash.Splash
import ru.bysoft.android.budget.common.util.restore
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.settings.SettingsNavigation
import ru.bysoft.android.budget.features.create_udate_category.navigation.CreateCategoryNavInfo
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.transaction.Transaction
import ru.bysoft.android.budget.features.create_udate_category.presentation.screen.CreateCategoryScreen
import ru.bysoft.android.budget.features.create_udate_category.presentation.screen.UpdateCategoryScreen
import ru.bysoft.android.budget.features.create_udate_category.presentation.viewmodels.CreateCategoryViewModel
import ru.bysoft.android.budget.features.create_udate_category.presentation.viewmodels.UpdateCategoryViewModel
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.TransactionScreen
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.TransactionCreateViewModel
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.TransactionUpdateViewModel
import ru.bysoft.android.budget.features.create_update_wallet.CreateWalletViewModel
import ru.bysoft.android.budget.features.create_update_wallet.UpdateWalletViewModel
import ru.bysoft.android.budget.features.create_update_wallet.presentation.screen.CRUDWalletScreen
import ru.bysoft.android.budget.features.settings.presentation.SettingsViewModel
import ru.bysoft.android.budget.features.settings.presentation.screen.SettingsScreen
import ru.bysoft.android.budget.features.splash.presentation.screen.SplashScreen
import ru.bysoft.android.budget.features.statistic_by_month.presentation.StatisticByFiltersViewModel
import ru.bysoft.android.budget.features.statistic_by_month.presentation.screen.StatisticByFiltersScreen

@Composable
fun MainNavigationHost(mainNavController: NavHostController) {
    NavHost(navController = mainNavController, startDestination = Splash.route) {

        composable(Splash.route) {
            SplashScreen()
        }

        composable(BottomNavigation.route) {
            val bottomNavigationController = rememberNavController()
            BottomNavigationScreen(
                bottomNavigationNavController = bottomNavigationController,
                mainNavController = mainNavController
            )
        }

        composable(Auth.route) {
            AuthScreen()
        }

        navigation(
            route = WalletNavigation.route,
            startDestination = WalletNavigation.screenName
        ) {
            composable(WalletNavigation.createScreenName) {
                val viewModel = hiltViewModel<CreateWalletViewModel>()
                CRUDWalletScreen(
                    screenController = viewModel.walletScreenController,
                    viewModelWalletState = viewModel.state.collectAsState().value,
                    viewModel = viewModel
                )
            }

            composable("${WalletNavigation.updateScreenName}/{${WalletNavigation.walletIdKey}}") {
                val walletId =
                    it.arguments?.getString(WalletNavigation.walletIdKey)
                        ?.restore<String>()
                val viewModel = hiltViewModel<UpdateWalletViewModel>()
                LaunchedEffect(Unit) {
                    viewModel.init(walletId)
                }

                CRUDWalletScreen(
                    screenController = viewModel.walletScreenController,
                    viewModelWalletState = viewModel.state.collectAsState().value,
                    viewModel = viewModel,
                    walletId = walletId
                )
            }

        }

        navigation(
            route = CreateUpdateCategory.route,
            startDestination = CreateUpdateCategory.screenName
        ) {
            composable("${CreateUpdateCategory.createScreenName}/{arguments}") {
                val typeCategory =
                    it.arguments?.getString("arguments")?.restore<CreateCategoryNavInfo>()
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
            route = DetailStatistic.route,
            startDestination = DetailStatistic.screenName
        ) {
            composable(DetailStatistic.screenName) {
                val viewModel = hiltViewModel<StatisticByFiltersViewModel>()
                StatisticByFiltersScreen(viewModel)
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
                val viewModel = hiltViewModel<TransactionCreateViewModel>()
                TransactionScreen(viewModel)
            }

            composable("${Transaction.updateScreen}/{arguments}") {
                val id = it.arguments?.getString("arguments")!!
                val viewModel = hiltViewModel<TransactionUpdateViewModel>()
                LaunchedEffect(Unit) { viewModel.initId(id) }
                TransactionScreen(viewModel)
            }
        }

        composable(SettingsNavigation.screenName) {
            val viewModel = hiltViewModel<SettingsViewModel>()
            SettingsScreen(viewModel)
        }
    }
}