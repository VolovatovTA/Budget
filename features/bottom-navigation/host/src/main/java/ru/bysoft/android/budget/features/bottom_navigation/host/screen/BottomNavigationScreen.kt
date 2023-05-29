package ru.bysoft.android.budget.features.bottom_navigation.host.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigationButtonInfo
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigationNavHost
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.home.Home
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.plus.Plus
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.qr.QRCode
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.statistic.Statistic
import ru.bysoft.android.budget.common.navigation.NavigationInfo
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.expandablecontent.VerticalExpandableContent
import ru.bysoft.android.budget.uikit.icons.another.Wallet
import ru.bysoft.android.budget.uikit.styles.UiKitTypography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomNavigationScreen(
    bottomNavigateionNavController: NavHostController,
    mainNavController: NavHostController
) {
    Scaffold(
        backgroundColor = UiKitColors.colors.primary.`100`,
        bottomBar = {
            val navBackStackEntry by bottomNavigateionNavController.currentBackStackEntryAsState()
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                items.forEach { screen ->
                    val isSelected =
//                        screen.icon == Wallet
                        currentDestination?.hierarchy?.any { it.route == (screen as? NavigationInfo)?.route } == true
                    val tinColor =
                        if (screen is NavigationInfo) {
                            if (isSelected) {
                                UiKitColors.colors.primary.`600`
                            } else {
                                UiKitColors.colors.neutral.`900`
                            }
                        } else {
                            UiKitColors.colors.neutral.`1100`
                        }

                    BottomNavigationItem(
                        screen,
                        bottomNavigateionNavController,
                        mainNavController,
                        tinColor,
                        isSelected
                    )
                }
            }
        }
    ) {
        BottomNavigationNavHost(bottomNavigateionNavController)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun BottomNavigationScreenPreview() {
    BottomNavigationScreen(
        bottomNavigateionNavController = rememberNavController(),
        mainNavController = rememberNavController()
    )
}

@Composable
private fun BottomNavigationItem(
    screen: BottomNavigationButtonInfo,
    bottomNavigationNavController: NavHostController,
    mainNavController: NavHostController,
    tinColor: Color,
    isSelected: Boolean
) {
    val isCollapsed = remember { mutableStateOf(true) }

    Column {

        if (screen == Plus) {
            VerticalExpandableContent(isCollapsed = isCollapsed.value) {
                Column {
                    Plus.entireList.forEach { bottomNavigationButtonInfo ->
                        BottomButton(
                            bottomNavigationButtonInfo,
                            mainNavController,
                            isCollapsed,
                            tinColor,
                            false,
                            isSelected
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
        BottomButton(screen, bottomNavigationNavController, isCollapsed, tinColor, true, isSelected)
    }


}

@Composable
private fun BottomButton(
    screen: BottomNavigationButtonInfo,
    navController: NavHostController,
    isCollapsed: MutableState<Boolean>,
    tintColor: Color,
    needPopUp: Boolean,
    isSelected: Boolean
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = if (isSelected) 4.dp else 0.dp,
    ) {
        Column(
            modifier = Modifier
                .size(40.dp)
                .background(if (isSelected) UiKitColors.colors.neutral.`300` else UiKitColors.colors.neutral.`400`)
                .clickable {
                    if (screen is NavigationInfo) {
                        navigateToScreen(navController, screen, needPopUp)
                    } else {
                        isCollapsed.value = !isCollapsed.value
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Icon(
                screen.icon,
                contentDescription = null,
                tint = tintColor,
                modifier = Modifier.rotate(if (!isCollapsed.value && screen is Plus) 180f else 0f)
            )

            screen.label?.let {
                Text(
                    text = stringResource(id = it),
                    style = UiKitTypography.TextXS.Regular,
                    modifier = Modifier.width(40.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    fontSize = 8.sp
                )
            }
        }
    }
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