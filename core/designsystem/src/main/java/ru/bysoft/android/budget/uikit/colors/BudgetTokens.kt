package ru.bysoft.android.budget.uikit.colors

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Design tokens of the 2026 refresh. Every colour a component needs comes from here.
 * [positive] / [negative] / [warning] are for amounts and limits only, [accent] is the single
 * brand colour, and [categoryPalette] / [walletGradients] are picked per entity through
 * [stablePaletteIndex]; the category hues deliberately avoid the semantic and accent hues so a
 * category never reads as "negative" or "selected". Both themes keep the same hue per slot, the
 * light theme uses darker variants that stay at 3:1 on white (see TokenContrastTest).
 */
@Immutable
data class BudgetTokens(
    val background: Color,
    val surface: Color,
    val surface2: Color,
    val line: Color,
    val textHigh: Color,
    val textMedium: Color,
    /** Disabled controls and untyped placeholder digits only: below 3:1 by design, never body text. */
    val textDisabled: Color,
    val accent: Color,
    val accentSoft: Color,
    val positive: Color,
    val negative: Color,
    val warning: Color,
    val categoryPalette: List<Color>,
    val walletGradients: List<Gradient>,
)

/** A two-stop linear gradient; wallet cards draw it from top-left to bottom-right. */
@Immutable
data class Gradient(val start: Color, val end: Color)

// Slot order (teal, blue, pink, orange, lime, cyan, sand) is shared by both themes.
private val DarkCategoryPalette = listOf(
    Color(0xFF2DBFAE),
    Color(0xFF5BA8FF),
    Color(0xFFFF8FB1),
    Color(0xFFFF9A62),
    Color(0xFFC5D94A),
    Color(0xFF4CC9F0),
    Color(0xFFD9A066),
)

private val LightCategoryPalette = listOf(
    Color(0xFF0E8C7E),
    Color(0xFF2A66D9),
    Color(0xFFD1467A),
    Color(0xFFD4621D),
    Color(0xFF7A8F0C),
    Color(0xFF0F7FA6),
    Color(0xFF8C5A1E),
)

private val WalletGradients = listOf(
    Gradient(Color(0xFF6B5BFF), Color(0xFFB48CFF)), // violet
    Gradient(Color(0xFF0E7C86), Color(0xFF19A3A3)), // teal
    Gradient(Color(0xFFD9534F), Color(0xFFFF8A65)), // coral
    Gradient(Color(0xFF2F6FED), Color(0xFF5BC0FF)), // blue
)

val DarkTokens = BudgetTokens(
    background = Color(0xFF0B0F19),
    surface = Color(0xFF141A26),
    surface2 = Color(0xFF1F273A),
    line = Color(0xFF232A3B),
    textHigh = Color(0xFFEEF1F7),
    textMedium = Color(0xFF8A93A6),
    textDisabled = Color(0xFF4A5366),
    accent = Color(0xFF7C6CFF),
    accentSoft = Color(0xFF7C6CFF).copy(alpha = 0.18f),
    positive = Color(0xFF3DCB8F),
    negative = Color(0xFFFF6B6B),
    warning = Color(0xFFF5B942),
    categoryPalette = DarkCategoryPalette,
    walletGradients = WalletGradients,
)

val LightTokens = BudgetTokens(
    background = Color(0xFFF4F5FA),
    surface = Color(0xFFFFFFFF),
    surface2 = Color(0xFFEEF0F7),
    line = Color(0xFFE1E5EF),
    textHigh = Color(0xFF141826),
    textMedium = Color(0xFF5E677A),
    textDisabled = Color(0xFFA3ABBD),
    accent = Color(0xFF6A5AF0),
    accentSoft = Color(0xFF6A5AF0).copy(alpha = 0.14f), // 14 % on white reads like 18 % on dark
    positive = Color(0xFF1B9263),
    negative = Color(0xFFD94A4A),
    warning = Color(0xFFB57A0A),
    categoryPalette = LightCategoryPalette,
    walletGradients = WalletGradients,
)
