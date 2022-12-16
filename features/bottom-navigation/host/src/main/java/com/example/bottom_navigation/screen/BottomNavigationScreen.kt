package com.example.bottom_navigation.screen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.bottom_navigation.navigation.home.Home
import com.example.bottom_navigation.navigation.plus.Plus
import com.example.bottom_navigation.navigation.qr.QRCode
import com.example.bottom_navigation.navigation.statistic.Statistic
import ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.HomeScreen
import ru.bysoft.budget.features.bottom_navigation.statistic.screen.StatisticScreen
import ru.bysoft.budget.features.bottom_navigation.home.HomeViewModel
import ru.bysoft.budget.features.bottom_navigation.home.navigation.IHomeNavigation
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.avatar.UiKitAvatar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomNavigationScreen(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val items = listOf(
                Home,
                Statistic,
                QRCode,
                Plus
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp, vertical = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items.forEach { screen ->
                    val tinColor =
                        if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                            UiKitColors.colors.col4_inactive
                        } else {
                            UiKitColors.colors.col4
                        }

                    UiKitAvatar(
                        icon = screen.icon,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        tintColor = tinColor,
                        modifier = Modifier,
                        backgroundColor = UiKitColors.colors.light,
                        elevation = 4.dp
                    )
                }
            }


        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Home.route,
        ) {
            composable(Home.route) {
                HomeScreen(hiltViewModel<HomeViewModel>())
            }
            composable(Statistic.route) { StatisticScreen() }
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
            composable(Plus.route) {

            }
        }
    }
}