package ru.bysoft.budget.features.bottom_navigation.statistic.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.features.bottom_navigation.statistic.IStatisticViewModel
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity.*
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.listItem.UiKitListItem
import ru.bysoft.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.budget.uikit.styles.UiKitStyles

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
                        .clickable { viewModel.updateCategory(it.id) }
                        ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    UiKitListItem(
                        title = it.name,
                        icons = listOfNotNull(it.icon),
                        amount = it.amount,
                        amountColor = UiKitColors.colors.dark,
                        subTitle = it.subtitle,
                        modifier = Modifier
                            .padding(end = 30.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    StatisticProgressIndicator(it.progressInfo)
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }

}

@Composable
private fun StatisticProgressIndicator(progress: ProgressInfo) {
    when (progress) {
        is ProgressInfoSuccess -> LinearProgressIndicator(
            progress = progress.progress,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
            color = UiKitColors.colors.red,
            backgroundColor = UiKitColors.colors.grey
        )
        is ProgressInfoWaiting -> UiKitShimmerComponent(
            Modifier
                .height(4.dp)
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            cornerRadius = 1.dp
        )
        is ProgressInfoError ->
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Spacer(
                    Modifier
                        .height(4.dp)
                        .weight(1f)
                        .background(UiKitColors.colors.red)
                )
                Text(
                    text = "Не удалось загрузить данные",
                    style = UiKitStyles.Caption,
                    color = UiKitColors.colors.red,
                    modifier = Modifier.padding(horizontal = 10.dp).padding(bottom = 2.dp)
                )
                Spacer(
                    Modifier
                        .height(4.dp)
                        .weight(1f)
                        .background(UiKitColors.colors.red)
                )
            }
    }
}


@Preview
@Composable
fun Preview() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        StatisticProgressIndicator(ProgressInfoSuccess(0.6f))
        StatisticProgressIndicator(ProgressInfoError)
        StatisticProgressIndicator(ProgressInfoWaiting)
    }
}