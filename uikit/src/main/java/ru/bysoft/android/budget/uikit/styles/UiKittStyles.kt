package ru.bysoft.android.budget.uikit.styles

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.theme.PublicSans

val textColor: Color
    @Composable
    get() = UiKitColors.colors.type.high

val padding = 16.dp
val halfPadding = padding / 2
val doublePadding = padding * 2
val quarterPadding = padding / 4

val corner = 8.dp
val halfCorner = corner / 2
val doubleCorner = corner * 2
val quarterCorner = corner / 4

data class BudgetTypography(
    val Regular: TextStyle,
    val Medium: TextStyle,
    val SemiBold: TextStyle,
    val Bold: TextStyle,
)

object UiKitTypography {
    val Display2XL: BudgetTypography
        @Composable
        get() {
            val base = TextStyle(
                color = textColor,
                fontSize = 72.sp,
                fontFamily = PublicSans,
                fontStyle = FontStyle.Normal,
                lineHeight = 84.sp,
                letterSpacing = (-0.02).sp,
            )
            return BudgetTypography(
                Regular = base.copy(fontWeight = FontWeight(400)),
                Medium = base.copy(fontWeight = FontWeight(500)),
                SemiBold = base.copy(fontWeight = FontWeight(600)),
                Bold = base.copy(fontWeight = FontWeight(700)),
            )
        }

    val DisplayXL: BudgetTypography
        @Composable
        get() {
            val base = TextStyle(
                color = textColor,
                fontSize = 60.sp,
                fontFamily = PublicSans,
                fontStyle = FontStyle.Normal,
                lineHeight = 68.sp,
                letterSpacing = (-0.02).sp,
            )
            return BudgetTypography(
                Regular = base.copy(fontWeight = FontWeight(400)),
                Medium = base.copy(fontWeight = FontWeight(500)),
                SemiBold = base.copy(fontWeight = FontWeight(600)),
                Bold = base.copy(fontWeight = FontWeight(700)),
            )
        }
    val DisplayLG: BudgetTypography
        @Composable
        get() {
            val base = TextStyle(
                color = textColor,
                fontSize = 48.sp,
                fontFamily = PublicSans,
                fontStyle = FontStyle.Normal,
                lineHeight = 56.sp,
                letterSpacing = (-0.02).sp,
            )
            return BudgetTypography(
                Regular = base.copy(fontWeight = FontWeight(400)),
                Medium = base.copy(fontWeight = FontWeight(500)),
                SemiBold = base.copy(fontWeight = FontWeight(600)),
                Bold = base.copy(fontWeight = FontWeight(700)),
            )
        }

    @Composable
    fun Body2Link(textStyle: TextStyle = TextMD.Regular) = SpanStyle(
        color = UiKitColors.colors.primary.`600`,
        fontSize = textStyle.fontSize,
        fontWeight = textStyle.fontWeight,
        fontFamily = textStyle.fontFamily,
        textDecoration = TextDecoration.Underline,
    )

    val DisplayMD: BudgetTypography
        @Composable
        get() {
            val base = TextStyle(
                color = textColor,
                fontSize = 36.sp,
                fontFamily = PublicSans,
                fontStyle = FontStyle.Normal,
                lineHeight = 40.sp,
                letterSpacing = (-0.02).sp,
            )
            return BudgetTypography(
                Regular = base.copy(fontWeight = FontWeight(400)),
                Medium = base.copy(fontWeight = FontWeight(500)),
                SemiBold = base.copy(fontWeight = FontWeight(600)),
                Bold = base.copy(fontWeight = FontWeight(700)),
            )
        }

    val DisplaySM: BudgetTypography
        @Composable
        get() {
            val base = TextStyle(
                color = textColor,
                fontSize = 30.sp,
                fontFamily = PublicSans,
                fontStyle = FontStyle.Normal,
                lineHeight = 34.sp,
                letterSpacing = (-0.02).sp,
            )
            return BudgetTypography(
                Regular = base.copy(fontWeight = FontWeight(400)),
                Medium = base.copy(fontWeight = FontWeight(500)),
                SemiBold = base.copy(fontWeight = FontWeight(600)),
                Bold = base.copy(fontWeight = FontWeight(700)),
            )
        }

    val DisplayXS: BudgetTypography
        @Composable
        get() {
            val base = TextStyle(
                color = textColor,
                fontSize = 24.sp,
                fontFamily = PublicSans,
                fontStyle = FontStyle.Normal,
                lineHeight = 28.sp,
                letterSpacing = (-0.02).sp,
            )
            return BudgetTypography(
                Regular = base.copy(fontWeight = FontWeight(400)),
                Medium = base.copy(fontWeight = FontWeight(500)),
                SemiBold = base.copy(fontWeight = FontWeight(600)),
                Bold = base.copy(fontWeight = FontWeight(700)),
            )
        }

    val TextXL: BudgetTypography
        @Composable
        get() {
            val base = TextStyle(
                color = textColor,
                fontSize = 20.sp,
                fontFamily = PublicSans,
                fontStyle = FontStyle.Normal,
                lineHeight = 26.sp,
            )
            return BudgetTypography(
                Regular = base.copy(fontWeight = FontWeight(400)),
                Medium = base.copy(fontWeight = FontWeight(500)),
                SemiBold = base.copy(fontWeight = FontWeight(600)),
                Bold = base.copy(fontWeight = FontWeight(700)),
            )
        }

    val TextLG: BudgetTypography
        @Composable
        get() {
            val base = TextStyle(
                color = textColor,
                fontSize = 18.sp,
                fontFamily = PublicSans,
                fontStyle = FontStyle.Normal,
                lineHeight = 24.sp,
            )
            return BudgetTypography(
                Regular = base.copy(fontWeight = FontWeight(400)),
                Medium = base.copy(fontWeight = FontWeight(500)),
                SemiBold = base.copy(fontWeight = FontWeight(600)),
                Bold = base.copy(fontWeight = FontWeight(700)),
            )
        }

    val TextMD: BudgetTypography
        @Composable
        get() {
            val base = TextStyle(
                color = textColor,
                fontSize = 16.sp,
                fontFamily = PublicSans,
                fontStyle = FontStyle.Normal,
                lineHeight = 20.sp,
            )
            return BudgetTypography(
                Regular = base.copy(fontWeight = FontWeight(400)),
                Medium = base.copy(fontWeight = FontWeight(500)),
                SemiBold = base.copy(fontWeight = FontWeight(600)),
                Bold = base.copy(fontWeight = FontWeight(700)),
            )
        }

    val TextSM: BudgetTypography
        @Composable
        get() {
            val base = TextStyle(
                color = textColor,
                fontSize = 14.sp,
                fontFamily = PublicSans,
                fontStyle = FontStyle.Normal,
                lineHeight = 16.sp,
            )
            return BudgetTypography(
                Regular = base.copy(fontWeight = FontWeight(400)),
                Medium = base.copy(fontWeight = FontWeight(500)),
                SemiBold = base.copy(fontWeight = FontWeight(600)),
                Bold = base.copy(fontWeight = FontWeight(700)),
            )
        }


    val TextXS: BudgetTypography
        @Composable
        get() {
            val base = TextStyle(
                color = textColor,
                fontSize = 12.sp,
                fontFamily = PublicSans,
                fontStyle = FontStyle.Normal,
                lineHeight = 14.sp,
                letterSpacing = (-0.02).sp,
            )
            return BudgetTypography(
                Regular = base.copy(fontWeight = FontWeight(400)),
                Medium = base.copy(fontWeight = FontWeight(500)),
                SemiBold = base.copy(fontWeight = FontWeight(600)),
                Bold = base.copy(fontWeight = FontWeight(700)),
            )
        }
}
