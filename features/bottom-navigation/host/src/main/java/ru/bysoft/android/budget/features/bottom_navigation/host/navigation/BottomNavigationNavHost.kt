package ru.bysoft.android.budget.features.bottom_navigation.host.navigation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.bysoft.android.budget.common.navigation.NavigationInfo
import ru.bysoft.android.budget.features.bottom_navigation.home.HomeViewModel
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.HomeScreen
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.home.Home
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.qr.QRCode
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.statistic.Statistic
import ru.bysoft.android.budget.features.bottom_navigation.statistic.StatisticViewModel
import ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.screen.StatisticScreen

@Composable
fun BottomNavigationNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
    ) {
        navigation(
            route = Home.route,
            startDestination = Home.screenName
        ) {
            composable(Home.screenName) {
                HomeScreen(hiltViewModel<HomeViewModel>())
            }
        }
        navigation(route = Statistic.route, startDestination = Statistic.screenName) {
            composable(Statistic.screenName) {
                StatisticScreen(hiltViewModel<StatisticViewModel>())
            }
        }

        navigation(route = QRCode.route, startDestination = QRCode.screenName) {
            composable(QRCode.screenName) {
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.TakePicture(),
                    onResult = {
                    }
                )
                LaunchedEffect(key1 = Unit) {
                    launcher.launch(Uri.parse(""))
                }
            }
        }
    }
}

object BottomNavigation : NavigationInfo("bottom navigation", "bottomNavScreen")

interface BottomNavigationButtonInfo {
    val iconId: Int
    val label: Int

    @Composable
    fun backgroundColor(): Color
}

