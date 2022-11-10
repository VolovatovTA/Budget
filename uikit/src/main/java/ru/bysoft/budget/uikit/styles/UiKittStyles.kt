package ru.bysoft.budget.uikit.styles

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.theme.Roboto


class UiKitStyles {
    companion object {
        val H1 = TextStyle(
            color = UiKitColors.black,
            fontSize = 24.sp,
            fontWeight = FontWeight(700),
            fontFamily = Roboto
        )
        val H2 = TextStyle(
            color = UiKitColors.black,
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
            fontFamily = Roboto
        )
        val Body2 = TextStyle(
            color = UiKitColors.black,
            fontSize = 14.sp,
            fontWeight = FontWeight(400),
            fontFamily = Roboto
        )
        val Caption = TextStyle(
            color = UiKitColors.black,
            fontSize = 10.sp,
            fontWeight = FontWeight(300),
            fontFamily = Roboto
        )
    }
}
