package ru.bysoft.budget

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.templates.mainTopBar

@Composable
fun MainScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = UiKitColors.white
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(UiKitColors.white)
        ) {
            Text(
                text = "Hello World!!",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            BottomNavigationBar()

        }
    }

}

@Composable
fun BottomNavigationBar() {
    //todo: Реализовать нижнюю навигацию на главной
}
