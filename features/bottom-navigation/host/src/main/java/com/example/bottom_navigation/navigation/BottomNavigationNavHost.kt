package com.example.bottom_navigation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bottom_navigation.navigation.home.Home
import com.example.bottom_navigation.navigation.statistic.Statistic
import ru.bysoft.budget.features.bottom_navigation.statistic.screen.StatisticScreen

// todo: Доделать этот хост чтоб работал правильно
@Composable
fun BottomNavigationNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home.route) {
        composable(Home.route) {  }
        composable(Statistic.route) { StatisticScreen() }
    }
}

open class NavigationInfo(
    val route: String,
    val screenName: String
)

object BottomNavigation: NavigationInfo("bottom navigation", "bottomNavScreen")

interface BottomNavigationButtonInfo{
    val icon: ImageVector
    val label: String?
    @Composable fun backgroundColor(): Color
}

