package ru.bysoft.android.budget.uikit.colors

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Design tokens of the 2026 refresh. Every colour a component needs comes from here;
 * the semantic ones (positive / negative / warning) are for amounts and limits only,
 * [accent] is the single brand colour, [categoryPalette] and [walletGradients] are picked
 * per entity through [stablePaletteIndex].
 */
@Immutable
data class BudgetTokens(
    val background: Color,
    val surface: Color,
    val surface2: Color,
    val line: Color,
    val textHigh: Color,
    val textMedium: Color,
    val textLow: Color,
    val accent: Color,
    val accentSoft: Color,
    val positive: Color,
    val negative: Color,
    val warning: Color,
    val categoryPalette: List<Color>,
    val walletGradients: List<List<Color>>,
)

private val CategoryPalette = listOf(
    Color(0xFF3DCB8F), // green
    Color(0xFFFF6B6B), // red
    Color(0xFF7C6CFF), // violet
    Color(0xFFF5B942), // amber
    Color(0xFF19A3A3), // teal
    Color(0xFFFF8FB1), // pink
    Color(0xFF5BA8FF), // blue
)

private val WalletGradients = listOf(
    listOf(Color(0xFF6B5BFF), Color(0xFFB48CFF)), // violet
    listOf(Color(0xFF0E7C86), Color(0xFF19A3A3)), // teal
    listOf(Color(0xFFD9534F), Color(0xFFFF8A65)), // coral
    listOf(Color(0xFF2F6FED), Color(0xFF5BC0FF)), // blue
)

val DarkTokens = BudgetTokens(
    background = Color(0xFF0B0F19),
    surface = Color(0xFF141A26),
    surface2 = Color(0xFF1F273A),
    line = Color(0xFF232A3B),
    textHigh = Color(0xFFEEF1F7),
    textMedium = Color(0xFF8A93A6),
    textLow = Color(0xFF4A5366),
    accent = Color(0xFF7C6CFF),
    accentSoft = Color(0xFF7C6CFF).copy(alpha = 0.18f),
    positive = Color(0xFF3DCB8F),
    negative = Color(0xFFFF6B6B),
    warning = Color(0xFFF5B942),
    categoryPalette = CategoryPalette,
    walletGradients = WalletGradients,
)

val LightTokens = BudgetTokens(
    background = Color(0xFFF4F5FA),
    surface = Color(0xFFFFFFFF),
    surface2 = Color(0xFFEEF0F7),
    line = Color(0xFFE1E5EF),
    textHigh = Color(0xFF141826),
    textMedium = Color(0xFF5E677A),
    textLow = Color(0xFFA3ABBD),
    accent = Color(0xFF6A5AF0),
    accentSoft = Color(0xFF6A5AF0).copy(alpha = 0.14f), // 14 % on white reads like 18 % on dark
    positive = Color(0xFF1E9E6A),
    negative = Color(0xFFD94A4A),
    warning = Color(0xFFC88A12),
    categoryPalette = CategoryPalette,
    walletGradients = WalletGradients,
)
