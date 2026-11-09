package ru.bysoft.android.budget.uikit.styles

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/** Corner radii of the 2026 refresh; features take shapes from here, never from literal dp. */
object BudgetShapes {
    val card = RoundedCornerShape(22.dp)
    val tile = RoundedCornerShape(18.dp)
    val control = RoundedCornerShape(16.dp)
    val small = RoundedCornerShape(12.dp)
    val pill = CircleShape
}

/** Spacing scale; `padding`/`halfPadding` in UiKittStyles.kt stay for legacy call sites. */
object BudgetSpacing {
    val xs = 4.dp
    val s = 8.dp
    val m = 12.dp
    val l = 16.dp
    val xl = 24.dp
}
