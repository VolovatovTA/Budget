package ru.bysoft.budget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ru.bysoft.budget.common.navigation.MainNavigationHost
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.theme.BudgetTheme.BudgetTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(color = UiKitColors.colors.colE)
            systemUiController.statusBarDarkContentEnabled = true
            systemUiController.navigationBarDarkContentEnabled = true
            BudgetTheme {
                MainNavigationHost()
            }
        }
    }
}
