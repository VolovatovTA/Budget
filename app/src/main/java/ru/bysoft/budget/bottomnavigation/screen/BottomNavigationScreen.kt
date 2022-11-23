package ru.bysoft.budget.bottomnavigation.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.bysoft.budget.common.navigation.home.Wallet
import ru.bysoft.budget.common.navigation.qr.QRCode
import ru.bysoft.budget.common.navigation.statistic.Plus
import ru.bysoft.budget.common.navigation.statistic.Statistic
import ru.bysoft.budget.home.HomeViewModel
import ru.bysoft.budget.home.screen.HomeScreen
import ru.bysoft.budget.statistic.screen.StatisticScreen
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.avatar.UiKitAvatar
import ru.bysoft.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomNavigationScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val items = listOf(
                Wallet,
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
                            UiKitColors.col6
                        } else {
                            UiKitColors.black
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
                        backgroundColor = screen.backgroundColor,
                        elevation = 4.dp
                    )
                }
            }


        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Wallet.route,
        ) {
            composable(Wallet.route) {
                HomeScreen(hiltViewModel<HomeViewModel>(), navController)
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
                Dialog(onDismissRequest = {  }) {
                    DialogCreateTransaction()
                }
            }
        }
    }
}

@Composable
fun DialogCreateTransaction() {
    Column {
        UiKitButton(info = UiKitButtonInfo(text = "РАСХОД", type = ButtonType.MEDIUM))
    }
}