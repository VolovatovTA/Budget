package com.example.bottom_navigation.screen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.bottom_navigation.navigation.BottomNavigationButtonInfo
import com.example.bottom_navigation.navigation.NavigationInfo
import com.example.bottom_navigation.navigation.home.Home
import com.example.bottom_navigation.navigation.plus.Plus
import com.example.bottom_navigation.navigation.qr.QRCode
import com.example.bottom_navigation.navigation.statistic.Statistic
import ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.HomeScreen
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.screen.StatisticScreen
import ru.bysoft.budget.features.bottom_navigation.home.HomeViewModel
import ru.bysoft.budget.features.bottom_navigation.statistic.StatisticViewModel
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.avatar.UiKitAvatar
import ru.bysoft.budget.uikit.components.expandablecontetn.VerticalExpandableContent
import ru.bysoft.budget.uikit.styles.UiKitStyles

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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                items.forEach { screen ->
                    val tinColor =
                        if (screen is NavigationInfo) {
                            if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                UiKitColors.colors.dark
                            } else {
                                UiKitColors.colors.col3
                            }
                        } else {
                            UiKitColors.colors.col4
                        }

                    BottomNavigationItem(screen, navController, tinColor)
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
}

@Composable
private fun BottomNavigationItem(
    screen: BottomNavigationButtonInfo,
    navController: NavHostController,
    tinColor: Color
) {
    val isCollapsed = remember { mutableStateOf(true) }

    Column {

        if (screen == Plus) {
            VerticalExpandableContent(isCollapsed = isCollapsed.value) {
                Column {
                    Plus.entireList.forEach { bottomNavigationButtonInfo ->
                        BottomButton(
                            bottomNavigationButtonInfo,
                            navController,
                            isCollapsed,
                            tinColor
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }

        BottomButton(screen, navController, isCollapsed, tinColor)

    }


}

@Composable
private fun BottomButton(
    screen: BottomNavigationButtonInfo,
    navController: NavHostController,
    isCollapsed: MutableState<Boolean>,
    tintColor: Color
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .size(40.dp)
                .background(UiKitColors.colors.light)
                .clickable {
                    if (screen is NavigationInfo) {
                        navigateToScreen(navController, screen)
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
                    text = it,
                    style = UiKitStyles.Caption,
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
    screen: NavigationInfo
) {
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
}