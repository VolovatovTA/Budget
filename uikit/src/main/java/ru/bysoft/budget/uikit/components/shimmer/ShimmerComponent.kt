package ru.bysoft.budget.uikit.components.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.shimmer
import ru.bysoft.budget.uikit.colors.UiKitColors

@Composable
fun ShimmerComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = UiKitColors.colors.col7,
    cornerRadius: Dp = 10.dp,
) {
    val yourShimmerTheme = defaultShimmerTheme.copy(
        animationSpec = infiniteRepeatable(
            animation = tween(
                800,
                easing = LinearEasing,
                delayMillis = 400,
            ),
            repeatMode = RepeatMode.Restart,
        )
    )

    CompositionLocalProvider(
        LocalShimmerTheme provides yourShimmerTheme
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(cornerRadius))
                .shimmer()
                .background(backgroundColor),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}