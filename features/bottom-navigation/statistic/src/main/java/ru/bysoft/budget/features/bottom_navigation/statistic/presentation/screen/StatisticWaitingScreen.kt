package ru.bysoft.budget.features.bottom_navigation.statistic.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.uikit.components.shimmer.UiKitShimmerComponent

@Composable
fun StatisticWaitingScreen() {
    (0..5).forEach { _ -> WaitingListItem() }
}

@Composable
private fun WaitingListItem() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        UiKitShimmerComponent(
            Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .padding(start = 10.dp)
                .size(40.dp)
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
                .padding(end = 30.dp, start = 10.dp)
                .fillMaxWidth()
                .weight(1f)
        )
    }
}