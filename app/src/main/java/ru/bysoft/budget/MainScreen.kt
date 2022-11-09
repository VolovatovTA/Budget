package ru.bysoft.budget

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo

const val TAG = "Timofey"

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
            UiKitButton(
                UiKitButtonInfo(
                    text = "Расход",
                    type = ButtonType.SMALL
                ),
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 30.dp)
            ) {
                Log.d(TAG, "MainScreen: click")
            }
        }
    }

}

@Composable
fun BottomNavigationBar() {
    //todo: Реализовать нижнюю навигацию на главной
}
