package ru.bysoft.budget.uikit.colors

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.bysoft.budget.uikit.styles.text.BaseTextFieldColors
import ru.bysoft.budget.uikit.theme.BudgetThemes

interface Palette {
    val col1: Color
    val col2: Color
    val col3: Color
    val col4: Color
    val col5: Color
    val col6: Color
    val col7: Color
    val colE: Color
    val red1: Color
    val blue123: Color
    val white: Color
    val red: Color
    val black: Color
    val dark: Color

    val borderColor: Color
    val textFieldColors: BaseTextFieldColors
}

object DarkPalette : Palette {
    override val col1 = Color(0xFF11799E)
    override val col2 = Color(0x80006F97)
    override val col3 = Color(0x80002F41)
    override val col4 = Color(0xFF296B66)
    override val col5 = Color(0xFFB071EB)
    override val col6 = Color(0xFF00AF85)
    override val col7 = Color(0xFF00AF85)
    override val colE = Color(0x80002F41)
    override val red1 = Color(0xFFEB5757)
    override val blue123 = Color(0xFF018786)
    override val white = colE
    override val red = Color.Red
    override val black = Color.Black
    override val dark = Color(0xFFEEEEEE)
    override val borderColor = Color(0xFFEEEEEE)

    override val textFieldColors = BaseTextFieldColors(
        textColor = dark,
        disabledTextColor = col2,
        cursorColor = dark,
        backgroundColor = colE,
        disabledIndicatorColor = dark,
        disabledLabelColor = col2,
        disabledLeadingIconColor = dark,
        disabledPlaceholderColor = col2,
        disabledTrailingIconColor = col2,

        leadingIconColor = dark,
        focusedIndicatorColor = dark,
        focusedLabelColor = dark,
        placeholderColor = dark,
        unfocusedIndicatorColor = dark,
        unfocusedLabelColor = dark,
        errorCursorColor = red1,
        trailingIconColor = dark,
        errorLabelColor = red1,
        errorTrailingIconColor = red1,
        errorIndicatorColor = red1,
        errorLeadingIconColor = red1,
    )
}

private object LightPalette : Palette {
    override val col1 = Color(0xFF11799E)
    override val col2 = Color(0x80006F97)
    override val col3 = Color(0xFF9DD9D4)
    override val col4 = Color(0xFF296B66)
    override val col5 = Color(0xFFB071EB)
    override val col6 = Color(0xFF00AF85)
    override val col7 = Color(0xFF00AF85)
    override val colE = Color(0xFFEEEEEE)
    override val red1 = Color(0xFFEB5757)
    override val blue123 = Color(0xFF018786)
    override val white = Color.White
    override val red = Color.Red
    override val black = colE
    override val dark: Color = Color.Black
    override val borderColor = Color(0xFF9DD9D4)

    override val textFieldColors = BaseTextFieldColors(
        textColor = dark,
        disabledTextColor = col7,
        cursorColor = dark,
        backgroundColor = colE,
        disabledIndicatorColor = dark,
        disabledLabelColor = col2,
        disabledLeadingIconColor = dark,
        disabledPlaceholderColor = col2,
        disabledTrailingIconColor = col2,

        leadingIconColor = dark,
        focusedIndicatorColor = dark,
        focusedLabelColor = dark,
        placeholderColor = dark,
        unfocusedIndicatorColor = dark,
        unfocusedLabelColor = dark,
        errorCursorColor = red1,
        trailingIconColor = dark,
        errorLabelColor = red1,
        errorTrailingIconColor = red1,
        errorIndicatorColor = red1,
        errorLeadingIconColor = red1,
    )
}

object Custom1Palette : Palette {
    override val col1 = Color(0xFF11799E)
    override val col2 = Color(0x80006F97)
    override val col3 = Color(0xFF9DD9D4)
    override val col4 = Color(0xFF296B66)
    override val col5 = Color(0xFFB071EB)
    override val col6 = Color(0xFF00AF85)
    override val col7 = Color(0xFF00AF85)
    override val colE = Color(0xFFEEEEEE)
    override val red1 = Color(0xFFEB5757)
    override val blue123 = Color(0xFF018786)
    override val white = Color.White
    override val red = Color.Red
    override val black = Color.Black
    override val dark: Color = col3
    override val borderColor = Color(0xFF9DD9D4)
    override val textFieldColors = BaseTextFieldColors(
        textColor = black,
        disabledTextColor = col7,
        cursorColor = black,
        backgroundColor = colE,
        disabledIndicatorColor = black,
        disabledLabelColor = col2,
        disabledLeadingIconColor = black,
        disabledPlaceholderColor = col2,
        disabledTrailingIconColor = col2,

        leadingIconColor = black,
        focusedIndicatorColor = black,
        focusedLabelColor = black,
        placeholderColor = black,
        unfocusedIndicatorColor = black,
        unfocusedLabelColor = black,
        errorCursorColor = red1,
        trailingIconColor = black,
        errorLabelColor = red1,
        errorTrailingIconColor = red1,
        errorIndicatorColor = red1,
        errorLeadingIconColor = red1,
    )
}


object UiKitColors {
    @Composable
    private fun getColorsByTheme(theme: BudgetThemes? = null): Palette =
        when(theme){
            BudgetThemes.CUSTOM1 -> Custom1Palette
            else -> getColorsBySystemTheme()
        }

    @Composable
    private fun getColorsBySystemTheme(): Palette {
        return if (isSystemInDarkTheme()) DarkPalette else LightPalette
    }

    val colors: Palette
        @Composable
        get() = getColorsByTheme()
}