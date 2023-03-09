package ru.bysoft.android.budget.uikit.styles

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.theme.Roboto


class UiKitStyles {
    companion object {
        val H1: TextStyle
            @Composable
            get() = TextStyle(
                color = UiKitColors.colors.dark,
                fontSize = 24.sp,
                fontWeight = FontWeight(700),
                fontFamily = Roboto
            )
        val H2: TextStyle
            @Composable
            get() = TextStyle(
                color = UiKitColors.colors.dark,
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                fontFamily = Roboto
            )
        val Body2: TextStyle
            @Composable
            get() = TextStyle(
                color = UiKitColors.colors.dark,
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                fontFamily = Roboto
            )

        val Body2Link: SpanStyle
            @Composable
            get() = SpanStyle(
                color = UiKitColors.colors.col1,
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                fontFamily = Roboto
            )

        val Caption: TextStyle
            @Composable
            get() = TextStyle(
                color = UiKitColors.colors.dark,
                fontSize = 10.sp,
                fontWeight = FontWeight(300),
                fontFamily = Roboto
            )
    }
}
