package ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.transactions

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.transactions.*
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.listItem.UiKitListItem
import ru.bysoft.budget.uikit.components.listItem.entity.UiKitAmountInfoSuccess
import ru.bysoft.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.budget.uikit.icons.pack.*
import ru.bysoft.budget.uikit.styles.UiKitStyles

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeTransactionsComponent(
    state: TransactionsState,
    modifier: Modifier = Modifier,
    onRefresh: (Boolean) -> Unit,
    onStartEndSwipe: (TransactionInfo) -> Unit,
    onEndStartSwipe: (TransactionInfo) -> Unit,
) {
    val isRefreshing = (state as? TransactionLoading)?.isRefreshing ?: false
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { onRefresh(true) }
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        val scrollState = rememberScrollState()
        Column(modifier.verticalScroll(scrollState)) {
            when (state) {
                is TransactionSuccess -> TransactionsSuccessComponent(
                    state = state,
                    onStartEndSwipe = onStartEndSwipe,
                    onEndStartSwipe = onEndStartSwipe
                )
                is TransactionLoading -> TransactionLoadingComponent()
                is TransactionError -> TransactionErrorComponent()
            }
        }
        PullRefreshIndicator(
            isRefreshing,
            pullRefreshState,
            Modifier
                .align(Alignment.TopCenter)
                .alpha(if (isRefreshing) 1f else 0f)
        )
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TransactionsSuccessComponent(
    state: TransactionSuccess,
    onStartEndSwipe: (TransactionInfo) -> Unit,
    onEndStartSwipe: (TransactionInfo) -> Unit,
) {
    if (state.list.isEmpty()) {
        Box(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "У вас пока нет ни одной транзакции...",
                textAlign = TextAlign.Center
            )
        }

    } else {

        state.list.forEach { data ->
            val dismissState = rememberDismissState {
                when (it) {
                    DismissValue.DismissedToStart -> onEndStartSwipe(data)
                    DismissValue.DismissedToEnd -> onStartEndSwipe(data)
                    else -> {}
                }
                true
            }
            SwipeToDismiss(
                state = dismissState,
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> UiKitColors.colors.light
                            DismissValue.DismissedToEnd -> UiKitColors.colors.col4
                            DismissValue.DismissedToStart -> UiKitColors.colors.red
                            else -> UiKitColors.colors.light
                        }
                    )
                    val alignment = Alignment.CenterEnd

                    val scale by animateFloatAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> 0.25f
                            else -> 1f
                        }
                    )

                    val alphaDeleteIcon by animateFloatAsState(
                        when (dismissState.targetValue) {
                            DismissValue.DismissedToStart -> 1f
                            else -> 0f
                        }
                    )
                    val alphaUpdateIcon by animateFloatAsState(
                        when (dismissState.targetValue) {
                            DismissValue.DismissedToEnd -> 1f
                            else -> 0f
                        }
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color),
                        contentAlignment = alignment
                    ) {
                        Icon(
                            Edit,
                            contentDescription = "Update Icon",
                            modifier = Modifier
                                .scale(scale)
                                .padding(horizontal = 30.dp)
                                .alpha(alphaUpdateIcon)
                                .align(Alignment.CenterStart)
                        )

                        Icon(
                            Delete,
                            contentDescription = "Delete Icon",
                            modifier = Modifier
                                .scale(scale)
                                .padding(horizontal = 30.dp)
                                .alpha(alphaDeleteIcon)
                                .align(Alignment.CenterEnd)
                        )
                    }
                },
                dismissContent = {
                    Box(
                        modifier = Modifier
                            .background(UiKitColors.colors.light)
                            .padding(vertical = 10.dp, horizontal = 30.dp)
                    ) {
                        UiKitListItem(
                            title = data.name,
                            subTitle = data.date,
                            icon = data.icon,
                            modifier = Modifier
                                .clickable { }
                                .background(UiKitColors.colors.light),
                            amount = UiKitAmountInfoSuccess(data.amount),
                            amountColor = UiKitColors.getColorByName(data.color)
                        )
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
    }

}

@Composable
private fun TransactionLoadingComponent() {
    Column {
        (0..4).forEach { _ ->
            WaitingListItem()
        }
    }
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

@Composable
private fun TransactionErrorComponent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(
            text = "Не удалось загрузить данные",
            modifier = Modifier
                .padding(
                    horizontal = 30.dp, vertical = 30.dp
                ),
            style = UiKitStyles.Body2
        )
    }
}