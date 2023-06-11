package ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.features.bottom_navigation.statistic.IStatisticViewModel
import ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.entity.StatisticErrorState
import ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.entity.StatisticSuccessState
import ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.entity.StatisticWaitingState
import ru.bysoft.android.budget.features.bottom_navigation.statistic.R
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.listItem.UiKitListItem
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoSuccess
import ru.bysoft.android.budget.uikit.icons.pack.Plus

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StatisticScreen(
    viewModel: IStatisticViewModel
) {
    LaunchedEffect(Unit) { viewModel.loadData(false) }
    val state = viewModel.state.collectAsState().value
    val isRefreshing = state is StatisticWaitingState && state.isRefreshing
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.loadData(true) }
    )
    Scaffold(
        backgroundColor = UiKitColors.colors.surface.primary,
        modifier = Modifier.safeDrawingPadding(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pullRefresh(
                    pullRefreshState,
                    enabled = state !is StatisticWaitingState
                )
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                item {
                    AddCategoryElement(viewModel::addCategory)
                    when (state) {
                        is StatisticWaitingState -> StatisticWaitingScreen()
                        is StatisticErrorState -> StatisticErrorScreen()
                        is StatisticSuccessState -> StatisticSuccessScreen(state, viewModel)
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }


    }

}

@Composable
fun AddCategoryElement(onClick: () -> Unit) {
    UiKitListItem(
        title = stringResource(R.string.create_new_category),
        icons = listOf(Plus),
        amount = UiKitAmountInfoSuccess(""),
        amountColor = Color.Transparent,
        modifier = Modifier
            .clickable { onClick.invoke() }
            .padding(vertical = 15.dp)

    )
}