package ru.bysoft.budget.features.bottom_navigation.statistic.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.features.bottom_navigation.statistic.IStatisticViewModel
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity.StatisticSuccessState
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.listItem.UiKitListItem
import ru.bysoft.budget.uikit.icons.pack.Plus

@Composable
fun StatisticSuccessScreen(
    state: StatisticSuccessState,
    viewModel: IStatisticViewModel
) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Column {
            state.listInfo.forEach {
                Column(
                    Modifier
                        .clickable { viewModel.updateCategory(it.id)}
                        .padding(horizontal = 30.dp)) {
                    Spacer(modifier = Modifier.height(15.dp))
                    UiKitListItem(
                        title = it.name,
                        icon = it.icon,
                        amount = it.amount,
                        countColor = UiKitColors.colors.dark,
                        subTitle = it.subtitle
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = 0.2f,
                        modifier = Modifier.fillMaxWidth(),
                        color = UiKitColors.colors.red,
                        backgroundColor = UiKitColors.colors.grey
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }

}