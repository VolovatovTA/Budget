package ru.bysoft.budget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.bysoft.budget.common.navigation.MainNavigationHost
import ru.bysoft.budget.uikit.theme.BudgetTheme.BudgetTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetTheme{
                MainNavigationHost()
            }
        }
    }
}
