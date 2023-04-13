package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.transactions

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.features.bottom_navigation.home.R
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.transactions.*
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.listItem.UiKitListItem
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoSuccess
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.icons.pack.*
import ru.bysoft.android.budget.uikit.styles.UiKitStyles

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeTransactionsComponent(
    state: TransactionsState,
    modifier: Modifier = Modifier,
    onRefresh: (Boolean) -> Unit,
) {
    val isRefreshing = (state as? TransactionLoading)?.isRefreshing ?: false
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { onRefresh(true) }
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(modifier) {
            when (state) {
                is TransactionSuccess -> transactionsSuccessComponent(
                    state = state
                )
                is TransactionLoading -> transactionLoadingComponent()
                is TransactionError -> transactionErrorComponent()
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
private fun LazyListScope.transactionsSuccessComponent(
    state: TransactionSuccess
) {
    if (state.list.isEmpty()) {
        item {
            Box(
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.empty_transactions_list),
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {

        items(
            items = state.list,
            key = { data -> data.id }
        ) { data ->
            val dismissState = data.dismissState

            LaunchedEffect(data.isWaiting) {
                if (!data.isWaiting) {
                    dismissState.reset()
                }
            }
            SwipeToDismiss(
                state = dismissState,
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> Color.Transparent
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
                        if (data.isWaiting) {
                            CircularProgressIndicator(
                                Modifier
                                    .padding(horizontal = 60.dp)
                                    .align(Alignment.CenterEnd)
                            )
                        }
                    }
                },
                dismissContent = {
                    Box(
                        modifier = Modifier
                            .clickable {  }
                            .background(Color.Transparent)
                            .padding(vertical = 10.dp)
                            .padding(end = 30.dp)
                    ) {
                        UiKitListItem(
                            title = data.name ?: "",
                            subTitle = data.date,
                            icons = data.icons,
                            modifier = Modifier
                                .background(Color.Transparent),
                            amount = UiKitAmountInfoSuccess(data.amount),
                            amountColor = UiKitColors.getColorByName(data.color)
                        )
                    }
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }

}

private fun LazyListScope.transactionLoadingComponent() {
    items(4) { WaitingListItem() }
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

private fun LazyListScope.transactionErrorComponent() {
    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.error_while_loading_some_data),
                modifier = Modifier
                    .padding(
                        horizontal = 30.dp, vertical = 30.dp
                    ),
                style = UiKitStyles.Body2
            )
        }
    }
}