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
    val col4_inactive: Color
    val red: Color
    val light: Color
    val light40: Color
    val dark: Color
    val dark40: Color
    val grey: Color

    val textFieldColors: BaseTextFieldColors
}

object DarkPalette : Palette {
    override val col1 = Color(0xFF11799E)
    override val col2 = Color(0x80006F97)
    override val col3 = Color(0x809DD9D4)
    override val col4 = Color(0xFF296B66)
    override val col5 = Color(0xFFDECFEB)
    override val col6 = Color(0xFFDECFEB)
    override val col4_inactive = Color(0x80296B66)
    override val red = Color(0xFFEB5757)
    override val dark = Color(0xFFFFFFFF)
    override val dark40 = Color(0x65FFFFFF)
    override val light = Color(0xFF000000)
    override val light40 = Color(0x65000000)
    override val grey = Color(0xFF4F4F4F)

    override val textFieldColors = BaseTextFieldColors(
        textColor = col5,
        disabledTextColor = red,
        cursorColor = dark,
        backgroundColor = Color.Transparent,
        disabledIndicatorColor = dark40,
        disabledLabelColor = col2,
        disabledLeadingIconColor = dark,
        disabledPlaceholderColor = col2,
        disabledTrailingIconColor = red,

        leadingIconColor = dark,
        focusedIndicatorColor = col5,
        focusedLabelColor = col5,
        placeholderColor = Color.Transparent,
        unfocusedIndicatorColor = col5,
        unfocusedLabelColor = col5,
        errorCursorColor = red,
        trailingIconColor = red,
        errorLabelColor = red,
        errorTrailingIconColor = red,
        errorIndicatorColor = red,
        errorLeadingIconColor = red,
    )
}

private object LightPalette : Palette {
    override val col1 = Color(0xFF11799E)
    override val col2 = Color(0x80006F97)
    override val col3 = Color(0x809DD9D4)
    override val col4 = Color(0xFF296B66)
    override val col5 = Color(0xFFDECFEB)
    override val col6 = Color(0xFF00AF85)
    override val col4_inactive = Color(0x80296B66)
    override val red = Color(0xFFEB5757)
    override val light = Color(0xFFFFFFFF)
    override val light40 = Color(0xFFFFFFFF)
    override val dark: Color = Color(0xFF000000)
    override val dark40: Color = Color(0x65000000)
    override val grey = Color(0xFF4F4F4F)

    override val textFieldColors = BaseTextFieldColors(
        textColor = dark,
        disabledTextColor = red,
        cursorColor = dark,
        backgroundColor = Color.Transparent,
        disabledIndicatorColor = dark40,
        disabledLabelColor = col2,
        disabledLeadingIconColor = dark,
        disabledPlaceholderColor = col2,
        disabledTrailingIconColor = red,

        leadingIconColor = dark,
        focusedIndicatorColor = dark,
        focusedLabelColor = dark,
        placeholderColor = Color.Transparent,
        unfocusedIndicatorColor = dark,
        unfocusedLabelColor = dark,
        errorCursorColor = red,
        trailingIconColor = red,
        errorLabelColor = red,
        errorTrailingIconColor = red,
        errorIndicatorColor = red,
        errorLeadingIconColor = red,
    )
}

object Custom1Palette : Palette {
    override val col1 = Color(0xFF11799E)
    override val col2 = Color(0x80006F97)
    override val col3 = Color(0xFF9DD9D4)
    override val col4 = Color(0xFF296B66)
    override val col5 = Color(0xFFB071EB)
    override val col6 = Color(0xFFB071EB)
    override val col4_inactive = Color(0xFF00AF85)
    override val red = Color(0xFFEB5757)
    override val light = Color.Black
    override val light40 = Color.Black
    override val dark: Color = col3
    override val dark40: Color = Color(0x65000000)
    override val grey = Color(0xFF4F4F4F)
    override val textFieldColors = BaseTextFieldColors(
        textColor = dark,
        disabledTextColor = col4_inactive,
        cursorColor = dark,
        backgroundColor = light,
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
        errorCursorColor = this.red,
        trailingIconColor = dark,
        errorLabelColor = this.red,
        errorTrailingIconColor = this.red,
        errorIndicatorColor = this.red,
        errorLeadingIconColor = this.red,
    )
}


object UiKitColors {
    @Composable
    private fun getColorsByTheme(theme: BudgetThemes? = null): Palette =
        when (theme) {
            BudgetThemes.CUSTOM1 -> Custom1Palette
            else -> getColorsBySystemTheme()
        }

    @Composable
    private fun getColorsBySystemTheme(): Palette {
        return if (isSystemInDarkTheme()) DarkPalette else LightPalette
    }

    @Composable
    fun getColorByName(color: String): Color =
        when (color) {
            "col1" -> this.colors.col1
            "col2" -> this.colors.col2
            "col3" -> this.colors.col3
            "col4" -> this.colors.col4
            "col4_inactive" -> this.colors.col4_inactive
            "col5" -> this.colors.col5
            "col6" -> this.colors.col6
            "dark" -> this.colors.dark
            "dark40" -> this.colors.dark40
            "grey" -> this.colors.grey
            "red" -> this.colors.red
            "light" -> this.colors.light
            "light40" -> this.colors.light40
            else -> throw Throwable()
        }


    val colors: Palette
        @Composable
        get() = getColorsByTheme()
}