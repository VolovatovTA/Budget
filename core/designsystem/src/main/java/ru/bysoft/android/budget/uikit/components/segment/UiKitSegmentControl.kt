package ru.bysoft.android.budget.uikit.components.segment

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.styles.BudgetShapes
import ru.bysoft.android.budget.uikit.styles.BudgetSpacing
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.theme.BudgetTheme

/**
 * A pill with equal-width segments. Single-select ([multiSelect] false): exactly one index in
 * [selected]; a filled indicator slides to the tapped segment. Multi-select: every selected segment
 * is filled on its own and a tap toggles it; the caller decides whether an empty selection is allowed.
 * Labels are final strings — resolve resources at the call site.
 */
@Composable
fun UiKitSegmentControl(
    options: ImmutableList<String>,
    selected: ImmutableSet<Int>,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    multiSelect: Boolean = false,
) {
    val tokens = UiKitColors.colors.tokens
    BoxWithConstraints(
        modifier = modifier
            .height(HEIGHT)
            .clip(BudgetShapes.pill)
            .background(tokens.surface)
            .padding(INSET),
    ) {
        val segmentWidth = maxWidth / options.size
        if (!multiSelect) {
            val index = selected.firstOrNull() ?: 0
            val offset by animateDpAsState(targetValue = segmentWidth * index, label = "segmentIndicator")
            Box(
                modifier = Modifier
                    .offset(x = offset)
                    .width(segmentWidth)
                    .height(HEIGHT - INSET * 2)
                    .clip(BudgetShapes.pill)
                    .background(tokens.accent),
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            options.forEachIndexed { index, label ->
                val isSelected = index in selected
                Segment(
                    label = label,
                    selected = isSelected,
                    fill = if (multiSelect && isSelected) tokens.accent else Color.Transparent,
                    onClick = { onSelect(index) },
                    modifier = Modifier.width(segmentWidth),
                )
            }
        }
    }
}

@Composable
private fun Segment(
    label: String,
    selected: Boolean,
    fill: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tokens = UiKitColors.colors.tokens
    val textColor by animateColorAsState(
        targetValue = if (selected) Color.White else tokens.textMedium,
        label = "segmentText",
    )
    Box(
        modifier = modifier
            .height(HEIGHT - INSET * 2)
            .clip(BudgetShapes.pill)
            .background(fill)
            .selectable(
                selected = selected,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Tab,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = UiKitTypography.TextSM.SemiBold,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = BudgetSpacing.s),
        )
    }
}

private val HEIGHT = 40.dp
private val INSET = 4.dp

@Preview(name = "light")
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SegmentControlPreview() {
    BudgetTheme {
        Column(modifier = Modifier.padding(BudgetSpacing.l)) {
            UiKitSegmentControl(
                options = persistentListOf("Расход", "Доход", "Перевод"),
                selected = persistentSetOf(1),
                onSelect = {},
                modifier = Modifier.fillMaxWidth(),
            )
            UiKitSegmentControl(
                options = persistentListOf("Расход", "Доход", "Перевод"),
                selected = persistentSetOf(0, 2),
                onSelect = {},
                multiSelect = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = BudgetSpacing.m),
            )
        }
    }
}
