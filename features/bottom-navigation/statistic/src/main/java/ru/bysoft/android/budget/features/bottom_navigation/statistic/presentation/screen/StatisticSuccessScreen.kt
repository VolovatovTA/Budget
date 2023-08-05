package ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding

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

            if (state.listInfo.isEmpty()) {
                Text(
                    text = stringResource(R.string.empty_category_list),
                    style = UiKitTypography.TextMD.Regular,
                    color = UiKitColors.colors.primary.`1100`,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = padding, vertical = halfPadding)
                )
            }
            state.listInfo.forEach { categoryInfo ->
                Column(
                    Modifier
                        .clickable { viewModel.updateCategory(categoryInfo.id) }
                ) {
                    Spacer(modifier = Modifier.height(halfPadding))
                    UiKitListItem(
                        title = categoryInfo.name,
                        icons = listOfNotNull(categoryInfo.icon),
                        amount = categoryInfo.amount,
                        amountColor = UiKitColors.colors.type.high,
                        modifier = Modifier
                            .padding(end = padding),
                        subTitle = categoryInfo.subtitle?.let {
                            stringResource(R.string.text_limit) + categoryInfo.subtitle + categoryInfo.subtitleAddition?.let {
                                stringResource(it)
                            }
                        },
                    )
                    categoryInfo.progressInfo?.let { progress ->
                        Spacer(modifier = Modifier.height(halfPadding))
                        StatisticProgressIndicator(progress)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatisticProgressIndicator(progress: ProgressInfo) {
    when (progress) {
        is ProgressInfoSuccess -> {
            val progressValue = progress.progress.coerceIn(0.01f, 1f)
            val color = Color(progressValue, 1 - progressValue, 0f)
            LinearProgressIndicator(
                progress = progressValue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = padding),
                color = color,
                backgroundColor = Color.Transparent,
                strokeCap = StrokeCap.Round
            )
        }

        is ProgressInfoWaiting -> UiKitShimmerComponent(
            Modifier
                .height(4.dp)
                .fillMaxWidth()
                .padding(horizontal = padding),
            cornerRadius = 4.dp
        )

        is ProgressInfoError ->
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
                    .padding(horizontal = padding)
            ) {
                Spacer(
                    Modifier
                        .height(4.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(2.dp))
                        .background(UiKitColors.colors.feedbackRed.`1100`)
                )
                Text(
                    text = stringResource(R.string.error_while_loading_some_data_statistic),
                    style = UiKitTypography.TextXS.Regular,
                    color = UiKitColors.colors.feedbackRed.`1100`,
                    modifier = Modifier
                        .padding(horizontal = halfPadding)
                        .padding(bottom = 2.dp)
                )
                Spacer(
                    Modifier
                        .height(4.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(2.dp))
                        .background(UiKitColors.colors.feedbackRed.`1100`)
                )
            }
    }
}


@Preview
@Composable
fun Preview() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        (0..10).forEach {
            StatisticProgressIndicator(ProgressInfoSuccess(it.toFloat() / 10))
        }
        StatisticProgressIndicator(ProgressInfoSuccess(1.6f))
        StatisticProgressIndicator(ProgressInfoError)
        StatisticProgressIndicator(ProgressInfoWaiting)
    }
}