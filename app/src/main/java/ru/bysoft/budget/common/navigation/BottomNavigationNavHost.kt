package ru.bysoft.budget.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.bysoft.budget.common.navigation.home.Wallet
import ru.bysoft.budget.common.navigation.statistic.Statistic
import ru.bysoft.budget.home.screen.HomeScreen
import ru.bysoft.budget.statistic.screen.StatisticScreen

// todo: Доделать этот хост чтоб работал правильно
@Composable
fun BottomNavigationNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Wallet.route) {
        composable(Wallet.route) { HomeScreen() }
        composable(Statistic.route) { StatisticScreen() }
    }
}

open class NavigationInfo(
    val route: String,
)

object BottomNavigation: NavigationInfo("bottom navigation")

interface BottomNavigationButtonInfo{
    val icon: ImageVector
    val label: String?
    val backgroundColor: Color
}

