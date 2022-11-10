package ru.bysoft.budget.uikit.colors

import androidx.compose.ui.graphics.Color
import ru.bysoft.budget.uikit.styles.text.BaseTextFieldColors
import ru.bysoft.budget.uikit.theme.BudgetThemes

class UiKitColors {
    companion object{
        fun getLightColorByTheme(theme: BudgetThemes? = null): Companion = this

        val col1 = Color(0xFF11799E)
        val col2 = Color(0x80006F97)
        val col3 = Color(0xFF9DD9D4)
        val col4 = Color(0xFF296B66)
        val col5 = Color(0xFFB071EB)
        val col6 = Color(0xFF00AF85)
        val col7 = Color(0x80296B66)
        val colE = Color(0xFFEEEEEE)

        val blue123 = Color(0xFF018786)
        val white = Color.White
        val red = Color.Red
        val black = Color.Black

        val basicTextFieldColors = BaseTextFieldColors(
            textColor = black,
            disabledTextColor = col7,
            cursorColor = black,
            backgroundColor = colE,
            disabledIndicatorColor = black,
            disabledLabelColor = col2,
            disabledLeadingIconColor = black,
            disabledPlaceholderColor = col2,
            disabledTrailingIconColor = col2,
            errorIndicatorColor = col5,
            errorLeadingIconColor = col5,
            leadingIconColor = black,
            focusedIndicatorColor = black,
            focusedLabelColor = black,
            placeholderColor = black,
            unfocusedIndicatorColor = black,
            unfocusedLabelColor = black,
            trailingIconColor = black,
            errorCursorColor = col5,
            errorLabelColor = col5,
            errorTrailingIconColor = col5
        )
    }
}