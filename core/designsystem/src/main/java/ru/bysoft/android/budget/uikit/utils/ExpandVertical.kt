package ru.bysoft.android.budget.uikit.utils

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable


const val duration = 300

@Composable
fun ExpandVertically(visible: Boolean, function: @Composable () -> Unit) {
    val enter = fadeIn(
        animationSpec = tween(delayMillis = duration)
    ) + expandVertically(
        animationSpec = tween(durationMillis = duration)
    )
    val exit = fadeOut(
        animationSpec = tween(durationMillis = duration)
    ) + shrinkVertically(
        animationSpec = tween(delayMillis = duration),
    )

    AnimatedVisibility(
        visible = visible,
        enter = enter,
        exit = exit
    ) {
        function()
    }
}