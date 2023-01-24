package com.example.bottom_navigation.navigation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bottom_navigation.navigation.home.Home
import com.example.bottom_navigation.navigation.qr.QRCode
import com.example.bottom_navigation.navigation.statistic.Statistic
import ru.bysoft.budget.common.navigation.NavigationInfo
import ru.bysoft.budget.features.bottom_navigation.home.HomeViewModel
import ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.HomeScreen
import ru.bysoft.budget.features.bottom_navigation.statistic.StatisticViewModel
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.screen.StatisticScreen

@Composable
fun BottomNavigationNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
    ) {
        composable(Home.route) {
            HomeScreen(hiltViewModel<HomeViewModel>())
        }
        composable(Statistic.route) {
            StatisticScreen(hiltViewModel<StatisticViewModel>())
        }
        composable(QRCode.route) {
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

object BottomNavigation: NavigationInfo("bottom navigation", "bottomNavScreen")

interface BottomNavigationButtonInfo{
    val icon: ImageVector
    val label: String?
    @Composable fun backgroundColor(): Color
}

