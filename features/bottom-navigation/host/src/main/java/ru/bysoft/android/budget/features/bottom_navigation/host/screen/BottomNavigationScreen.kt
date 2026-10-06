package ru.bysoft.android.budget.features.bottom_navigation.host.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.bysoft.android.budget.common.navigation.NavigationInfo
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigationNavHost
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.home.Home
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.plus.Plus
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.qr.QRCode
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.statistic.Statistic
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.styles.UiKitTypography

@Composable
fun BottomNavigationScreen(
    bottomNavigationNavController: NavHostController,
    mainNavController: NavHostController
) {
    Scaffold(
        backgroundColor = UiKitColors.colors.surface.primary,
        bottomBar = {
            val navBackStackEntry by bottomNavigationNavController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val items = listOf(
                Home,
                Statistic,
//                QRCode,
            )
            BottomAppBar(
                backgroundColor = UiKitColors.colors.surface.primary,
                elevation = 3.dp,
                modifier = Modifier
                    .fillMaxWidth(),
                cutoutShape = MaterialTheme.shapes.small,
                contentPadding = PaddingValues(0.dp),
            ) {
                items.forEach { screen ->
                    val isSelected =
//                        screen.icon == Wallet
                        currentDestination?.hierarchy?.any { it.route == (screen as? NavigationInfo)?.route } == true
                    val tintColor =
                        if (isSelected) {
                            UiKitColors.colors.primary.`600`
                        } else {
                            UiKitColors.colors.type.high
                        }

                    BottomNavigationItem(
                        selected = isSelected,
                        icon = {
                            Icon(
                                painterResource(id = screen.iconId),
                                contentDescription = stringResource(id = screen.label),
                                tint = tintColor,
                            )
                        },
                        enabled = screen != QRCode,
                        label = {
                            Text(
                                text = stringResource(id = screen.label),
                                style = UiKitTypography.TextXS.Regular,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                            )
                        },
                        alwaysShowLabel = false,
                        onClick = {
                            navigateToScreen(bottomNavigationNavController, screen, false)
                        }
                    )
                }
            }
        },
        modifier = Modifier
            .safeDrawingPadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToScreen(mainNavController, Plus, false)
                },
                backgroundColor = UiKitColors.colors.surface.secondary,
            ) {
                Icon(
                    painter = painterResource(id = Plus.iconId),
                    contentDescription = "add",
                    tint = UiKitColors.colors.type.high,
                )
            }
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            BottomNavigationNavHost(bottomNavigationNavController)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun BottomNavigationScreenPreview() {
    BottomNavigationScreen(
        bottomNavigationNavController = rememberNavController(),
        mainNavController = rememberNavController()
    )
}


private fun navigateToScreen(
    navController: NavHostController,
    screen: NavigationInfo,
    needPopUp: Boolean = true
) {

    navController.navigate(route = screen.screenName) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        if (needPopUp) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}