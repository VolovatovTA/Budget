package ru.bysoft.budget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ru.bysoft.budget.common.navigation.MainNavigationHost
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.theme.BudgetTheme.BudgetTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navHost: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(color = UiKitColors.colors.light40)
//            systemUiController.statusBarDarkContentEnabled = true
//            systemUiController.navigationBarDarkContentEnabled = true
            BudgetTheme {
                MainNavigationHost(navHost)
            }
        }
    }
}
