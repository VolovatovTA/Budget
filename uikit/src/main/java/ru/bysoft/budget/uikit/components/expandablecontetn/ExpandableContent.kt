package ru.bysoft.budget.uikit.components.expandablecontetn

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

const val DURATION_ANIMATION = 250

@Composable
fun VerticalExpandableContent(
    isCollapsed: Boolean,
    content: @Composable (() -> Unit)
) {
    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = defaultAnimationSpec()
        ) + fadeIn(
            animationSpec = defaultAnimationSpec()
        )
    }

    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = defaultAnimationSpec()
        )
    }

    AnimatedVisibility(
        visible = !isCollapsed,
        enter = expandTransition,
        exit = collapseTransition
    ) {
        content()
    }
}

fun <T> defaultAnimationSpec(duration: Int = DURATION_ANIMATION) = tween<T>(
    durationMillis = DURATION_ANIMATION,
    easing = FastOutSlowInEasing
)