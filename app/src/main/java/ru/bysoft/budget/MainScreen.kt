package ru.bysoft.budget

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import ru.bysoft.budget.common.navigation.MainNavigationHost
import ru.bysoft.budget.uikit.icons.pack.*

const val TAG = "Timofey"

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    MainNavigationHost()
}
