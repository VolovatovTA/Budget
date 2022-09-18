package ru.bysoft.budget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.bysoft.budget.uikit.theme.BudgetTheme.BudgetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BudgetTheme{
                MainScreen()
            }
        }
    }
}
