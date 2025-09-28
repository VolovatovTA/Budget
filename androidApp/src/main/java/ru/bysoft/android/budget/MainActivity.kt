package ru.bysoft.android.budget

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.activityScope
import ru.bysoft.android.budget.common.navigation.MainNavigationHost
import ru.bysoft.android.budget.common.navigation.NavHostControllerWrapper
import ru.bysoft.android.budget.common.util.TAG
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.theme.BudgetTheme

class MainActivity : ComponentActivity() {

    private val navWrapper: NavHostControllerWrapper by inject ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            navWrapper.set(navController)

            BudgetTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(color = UiKitColors.colors.surface.primary)
                MainNavigationHost(navController)
            }

            LaunchedEffect(Unit) {
                navController.currentBackStack.collect {
                    it.forEachIndexed { index, navBackStackEntry ->
                        Log.d(TAG, "$index. ${navBackStackEntry.destination.route}")
                    }
                }
            }
        }
    }

}
