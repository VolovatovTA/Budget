package ru.bysoft.android.budget

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.common.navigation.MainNavigationHost
import ru.bysoft.android.budget.common.util.TAG
import ru.bysoft.android.budget.uikit.colors.UiKitColors
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
            systemUiController.setSystemBarsColor(color = UiKitColors.colors.surface.primary)
            MainNavigationHost(navHost)
        }
        CoroutineScope(Dispatchers.Default).launch {
            navHost.currentBackStackEntryFlow.collect {
                navHost.backQueue.forEachIndexed { index, navBackStackEntry ->
                    Log.d(TAG, "$index. ${navBackStackEntry.destination.route}")
                }
            }
        }
    }

}
