package ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.features.bottom_navigation.statistic.IStatisticViewModel
import ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.entity.*
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.listItem.UiKitListItem
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.features.bottom_navigation.statistic.R
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo

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
            ShowMoreStatisticButton(viewModel::toDetailStatistic)

            if (state.listInfo.isEmpty()) {
                Text(
                    text = stringResource(R.string.empty_category_list),
                    style = UiKitTypography.TextMD.Regular,
                    color = UiKitColors.colors.primary.`1100`,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 10.dp)
                )
            }
            state.listInfo.forEach { categoryInfo ->
                Column(
                    Modifier
                        .clickable { viewModel.updateCategory(categoryInfo.id) }
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    UiKitListItem(
                        title = categoryInfo.name,
                        icons = listOfNotNull(categoryInfo.icon),
                        amount = categoryInfo.amount,
                        amountColor = UiKitColors.colors.primary.`1100`,
                        modifier = Modifier
                            .padding(end = 30.dp),
                        subTitle = categoryInfo.subtitle?.let {
                            stringResource(R.string.text_limit) + categoryInfo.subtitle + categoryInfo.subtitleAddition?.let {
                                stringResource(
                                    it
                                )
                            }
                        },
                    )
                    categoryInfo.progressInfo?.let { progress ->
                        Spacer(modifier = Modifier.height(10.dp))
                        StatisticProgressIndicator(progress)
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ShowMoreStatisticButton(onClick: () -> Unit) {
    UiKitButton(
        info = UiKitButtonInfo(
            text = stringResource(R.string.show_more_statistic),
            size = ButtonSize.MEDIUM
        ),
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )
}

@Composable
private fun StatisticProgressIndicator(progress: ProgressInfo) {
    when (progress) {
        is ProgressInfoSuccess -> LinearProgressIndicator(
            progress = if (progress.progress > 1f) 1f else progress.progress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            color = if (progress.progress > 1f) UiKitColors.colors.feedbackRed.`1100` else UiKitColors.colors.neutral.`800`,
            backgroundColor = Color.Transparent
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
                        .background(UiKitColors.colors.feedbackRed.`1100`)
                )
                Text(
                    text = stringResource(R.string.error_while_loading_some_data_statistic),
                    style = UiKitTypography.TextXS.Regular,
                    color = UiKitColors.colors.feedbackRed.`1100`,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(bottom = 2.dp)
                )
                Spacer(
                    Modifier
                        .height(4.dp)
                        .weight(1f)
                        .background(UiKitColors.colors.feedbackRed.`1100`)
                )
            }
    }
}


@Preview
@Composable
fun Preview() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        StatisticProgressIndicator(ProgressInfoSuccess(0.6f))
        StatisticProgressIndicator(ProgressInfoSuccess(1.6f))
        StatisticProgressIndicator(ProgressInfoError)
        StatisticProgressIndicator(ProgressInfoWaiting)
    }
}