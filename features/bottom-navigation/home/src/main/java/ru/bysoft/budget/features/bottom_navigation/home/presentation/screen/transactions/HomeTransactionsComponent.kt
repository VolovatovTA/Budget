package ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.transactions.TransactionError
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.transactions.TransactionLoading
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.transactions.TransactionSuccess
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.transactions.TransactionsState
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.listItem.UiKitListItem
import ru.bysoft.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.budget.uikit.icons.pack.*
import ru.bysoft.budget.uikit.styles.UiKitStyles

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeTransactionsComponent(
    state: TransactionsState,
    modifier: Modifier = Modifier,
    onRefresh: (Boolean) -> Unit
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
                is TransactionSuccess -> TransactionsSuccessComponent(state)
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

@Composable
private fun TransactionsSuccessComponent(state: TransactionSuccess) {
    if (state.list.isEmpty()) {
        Text(
            text = "Тут пока что пусто...",
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    } else {
        state.list.forEach { data ->
            UiKitListItem(
                title = data.name,
                subTitle = data.date,
                icon = Food,
                modifier = Modifier
                    .clickable { }
                    .padding(vertical = 10.dp, horizontal = 30.dp),
                count = data.amount,
                countColor = UiKitColors.getColorByName(data.color)
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
    }

}

@Composable
private fun TransactionLoadingComponent() {
    Column(modifier = Modifier) {
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