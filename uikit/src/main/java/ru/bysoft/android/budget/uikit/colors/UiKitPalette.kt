package ru.bysoft.android.budget.uikit.colors

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.bysoft.android.budget.uikit.styles.text.BaseTextFieldColors

interface Palette {
    val primary: BudgetTone
    val neutral: BudgetTone
    val feedbackRed: BudgetTone
    val feedbackYellow: BudgetTone
    val feedbackGreen: BudgetTone
    val surface: BudgetSurfaceColors
    val type: BudgetTypeColors
}

data class BudgetTone(
    val `1100`: Color,
    val `1000`: Color,
    val `900`: Color,
    val `800`: Color,
    val `700`: Color,
    val `600`: Color,
    val `500`: Color,
    val `400`: Color,
    val `300`: Color,
    val `200`: Color,
    val `100`: Color,
)

data class BudgetSurfaceColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
)

data class BudgetTypeColors(
    val high: Color,
    val medium: Color,
    val low: Color,
)


object LightPalette : Palette {
    override val primary = BudgetTone(
        `1100` = Color(0xFF110045),
        `1000` = Color(0xFF250E6A),
        `900` = Color(0xFF391C8F),
        `800` = Color(0xFF4C29B5),
        `700` = Color(0xFF6037DA),
        `600` = Color(0xFF7445FF),
        `500` = Color(0xFF906AFF),
        `400` = Color(0xFFAC8FFF),
        `300` = Color(0xFFC7B5FF),
        `200` = Color(0xFFE3DAFF),
        `100` = Color(0xFFF1ECFF),
    )
    override val neutral = BudgetTone(
        `1100` = Color(0xFF070A13),
        `1000` = Color(0xFF0F172A),
        `900` = Color(0xFF1E293B),
        `800` = Color(0xFF334155),
        `700` = Color(0xFF475569),
        `600` = Color(0xFF64748B),
        `500` = Color(0xFF94A3B8),
        `400` = Color(0xFFCBD5E1),
        `300` = Color(0xFFE2E8F0),
        `200` = Color(0xFFF1F5F9),
        `100` = Color(0xFFF8FAFC),
    )
    override val feedbackRed = BudgetTone(
        `1100` = Color(0xFF150203),
        `1000` = Color(0xFF290507),
        `900` = Color(0xFF520A0D),
        `800` = Color(0xFF7C0E14),
        `700` = Color(0xFFA5131A),
        `600` = Color(0xFFCE1821),
        `500` = Color(0xFFD8464D),
        `400` = Color(0xFFE2747A),
        `300` = Color(0xFFEBA3A6),
        `200` = Color(0xFFF5D1D3),
        `100` = Color(0xFFFFEBEC),
    )
    override val feedbackYellow = BudgetTone(
        `1100` = Color(0xFF332307),
        `1000` = Color(0xFF65460E),
        `900` = Color(0xFF986A14),
        `800` = Color(0xFFCA8D1B),
        `700` = Color(0xFFFDB022),
        `600` = Color(0xFFFDC04E),
        `500` = Color(0xFFFED07A),
        `400` = Color(0xFFFEDFA7),
        `300` = Color(0xFFFEE7BD),
        `200` = Color(0xFFFFEFD3),
        `100` = Color(0xFFFFF7E9),
    )
    override val feedbackGreen = BudgetTone(
        `1100` = Color(0xFF030D07),
        `1000` = Color(0xFF061A0E),
        `900` = Color(0xFF092A16),
        `800` = Color(0xFF0C3A1F),
        `700` = Color(0xFF0F4A27),
        `600` = Color(0xFF125A30),
        `500` = Color(0xFF156A39),
        `400` = Color(0xFF3E9B67),
        `300` = Color(0xFF63B286),
        `200` = Color(0xFFACDFC3),
        `100` = Color(0xFFBEEBD2),
    )
    override val surface = BudgetSurfaceColors(
        primary = Color(0xFFFFFFFF),
        secondary = Color(0xFFF8FAFC),
        tertiary = Color(0xFFF1F5F9),
    )
    override val type = BudgetTypeColors(
        high = Color(0xFF070A13),
        medium = Color(0xFF64748B),
        low = Color(0xFFCBD5E1),
    )
}

object DarkPalette : Palette {
    override val primary = BudgetTone(
        `1100` = Color(0xFF261051),
        `1000` = Color(0xFF3E1E71),
        `900` = Color(0xFF553098),
        `800` = Color(0xFF6D40C8),
        `700` = Color(0xFF8C57E6),
        `600` = Color(0xFFA471F6),
        `500` = Color(0xFFBC8CFE),
        `400` = Color(0xFFD2A9FF),
        `300` = Color(0xFFE2C4FE),
        `200` = Color(0xFFE5D6FF),
        `100` = Color(0xFFE8E0FF),
    )
    override val neutral = BudgetTone(
        `1100` = Color(0xFF0E1216),
        `1000` = Color(0xFF141A1F),
        `900` = Color(0xFF1A2128),
        `800` = Color(0xFF202831),
        `700` = Color(0xFF26303B),
        `600` = Color(0xFF2C3844),
        `500` = Color(0xFF32404D),
        `400` = Color(0xFF384857),
        `300` = Color(0xFF3E5060),
        `200` = Color(0xFF445869),
        `100` = Color(0xFF4A6073),
    )
    override val feedbackRed = BudgetTone(
        `1100` = Color(0xFF2F0406),
        `1000` = Color(0xFF420508),
        `900` = Color(0xFF6D090E),
        `800` = Color(0xFF970C13),
        `700` = Color(0xFFC11018),
        `600` = Color(0xFFEC131E),
        `500` = Color(0xFFEB4C54),
        `400` = Color(0xFFF17E84),
        `300` = Color(0xFFF5A8AC),
        `200` = Color(0xFFF9CDCF),
        `100` = Color(0xFFFFE5E7),
    )
    override val feedbackYellow = BudgetTone(
        `1100` = Color(0xFF4A3307),
        `1000` = Color(0xFF82590D),
        `900` = Color(0xFFB57C12),
        `800` = Color(0xFFE89F17),
        `700` = Color(0xFFFDB93A),
        `600` = Color(0xFFFEC967),
        `500` = Color(0xFFFED790),
        `400` = Color(0xFFFEE4B3),
        `300` = Color(0xFFFEE9C2),
        `200` = Color(0xFFFFEDCC),
        `100` = Color(0xFFFFF2DC),
    )
    override val feedbackGreen = BudgetTone(
        `1100` = Color(0xFF062313),
        `1000` = Color(0xFF08301A),
        `900` = Color(0xFF0B4223),
        `800` = Color(0xFF106034),
        `700` = Color(0xFF157F44),
        `600` = Color(0xFF189550),
        `500` = Color(0xFF3DA96D),
        `400` = Color(0xFF64B98A),
        `300` = Color(0xFF8DD3AC),
        `200` = Color(0xFFADE5C6),
        `100` = Color(0xFFC5EDD7),
    )
    override val surface = BudgetSurfaceColors(
        primary = Color(0xFF0E1216),
        secondary = Color(0xFF1A2028),
        tertiary = Color(0xFF26303B),
    )
    override val type = BudgetTypeColors(
        high = Color(0xFFF8FAFC),
        medium = Color(0xFF94A3B8),
        low = Color(0xFF334155),
    )
}


object UiKitColors {


    @Composable
    private fun getColorsBySystemTheme(): Palette {
        return if (isSystemInDarkTheme()) DarkPalette else LightPalette
    }

    /**
     * Формат цвета: primary.1100
     */
    @Composable
    fun getColorByName(color: String): Color {
        val parts = color.split('.')
        if (parts.size < 2) return Color.Yellow
        val budgetColors = getBudgetColorsByName(name = parts[0])
        return when (parts[1]) {
            "1100" -> budgetColors.`1100`
            "1000" -> budgetColors.`1000`
            "900" -> budgetColors.`900`
            "800" -> budgetColors.`800`
            "700" -> budgetColors.`700`
            "600" -> budgetColors.`600`
            "500" -> budgetColors.`500`
            "400" -> budgetColors.`400`
            "300" -> budgetColors.`300`
            "200" -> budgetColors.`200`
            "100" -> budgetColors.`100`
            else -> Color.Yellow
        }
    }


    @Composable
    fun getBudgetColorsByName(name: String) =
        when (name) {
            "primary" -> this.colors.primary
            "neutral" -> this.colors.neutral
            "feedbackRed" -> this.colors.feedbackRed
            "feedbackYellow" -> this.colors.feedbackYellow
            "feedbackGreen" -> this.colors.feedbackGreen
            else -> this.colors.primary
        }


    val colors: Palette
        @Composable
        get() = getColorsBySystemTheme()

    @Composable
    fun card(isSelected: Boolean): Color = if (isSystemInDarkTheme()) {
        if (isSelected) colors.neutral.`300` else colors.surface.tertiary
    } else {
        if (isSelected) colors.neutral.`300` else colors.surface.primary
    }

    val selectionColors
        @Composable
        get() =
            TextSelectionColors(
                handleColor = colors.primary.`700`,
                backgroundColor = colors.type.low,
            )

    val textField
        @Composable
        get() =
            if (isSystemInDarkTheme())
                BaseTextFieldColors(
                    textColor = colors.type.high,
                    backgroundColor = Color.Transparent,
                    trailingIconColor = colors.type.high,
                    leadingIconColor = colors.type.high,
                    cursorColor = colors.type.high,
                    placeholderColor = Color.Transparent,
                    focusedIndicatorColor = colors.type.high,
                    focusedLabelColor = colors.type.high,
                    unfocusedIndicatorColor = colors.type.medium,
                    unfocusedLabelColor = colors.type.high,
                    disabledTextColor = colors.type.medium,
                    disabledIndicatorColor = colors.type.high,
                    disabledLabelColor = colors.type.low,
                    disabledLeadingIconColor = colors.type.low,
                    disabledPlaceholderColor = Color.Transparent,
                    disabledTrailingIconColor = colors.type.low,
                    errorTrailingIconColor = colors.feedbackRed.`600`,
                    errorLeadingIconColor = colors.feedbackRed.`600`,
                    errorLabelColor = colors.type.high,
                    errorIndicatorColor = colors.feedbackRed.`600`,
                    errorCursorColor = colors.feedbackRed.`600`
                )
            else
                BaseTextFieldColors(
                    textColor = colors.type.high,
                    backgroundColor = Color.Transparent,
                    trailingIconColor = colors.type.high,
                    leadingIconColor = colors.type.high,
                    cursorColor = colors.type.high,
                    placeholderColor = Color.Transparent,
                    focusedIndicatorColor = colors.type.high,
                    focusedLabelColor = colors.type.high,
                    unfocusedIndicatorColor = colors.type.medium,
                    unfocusedLabelColor = colors.type.high,
                    disabledTextColor = colors.type.medium,
                    disabledIndicatorColor = colors.type.high,
                    disabledLabelColor = colors.type.low,
                    disabledLeadingIconColor = colors.type.low,
                    disabledPlaceholderColor = Color.Transparent,
                    disabledTrailingIconColor = colors.type.low,
                    errorTrailingIconColor = colors.feedbackRed.`600`,
                    errorLeadingIconColor = colors.feedbackRed.`600`,
                    errorLabelColor = colors.type.high,
                    errorIndicatorColor = colors.feedbackRed.`600`,
                    errorCursorColor = colors.feedbackRed.`600`
                )
}