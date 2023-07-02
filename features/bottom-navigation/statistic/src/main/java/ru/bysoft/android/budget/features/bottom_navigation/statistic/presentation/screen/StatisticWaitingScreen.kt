package ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding

@Composable
fun StatisticWaitingScreen() {
    (0..5).forEach { _ -> WaitingListItem() }
}

@Composable
private fun WaitingListItem() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        UiKitShimmerComponent(
            Modifier
                .padding(horizontal = padding, vertical = padding)
                .clip(RoundedCornerShape(32.dp))
                .size(32.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.5f)
        ) {
            UiKitShimmerComponent(
                Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.5f)
            )
            Spacer(modifier = Modifier.height(9.dp))
            UiKitShimmerComponent(
                Modifier
                    .height(16.dp)
                    .fillMaxWidth()
            )
        }
        UiKitShimmerComponent(
            Modifier
                .height(16.dp)
                .padding(end = padding, start = halfPadding)
                .fillMaxWidth()
                .weight(1f)
        )
    }
}