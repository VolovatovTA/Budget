package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.transactions

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.features.bottom_navigation.home.R
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.transactions.TransactionError
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.transactions.TransactionLoading
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.transactions.TransactionSuccess
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.transactions.TransactionsState
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.listItem.UiKitListItem
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoSuccess
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.icons.pack.Delete
import ru.bysoft.android.budget.uikit.icons.pack.Edit
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.doublePadding
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding

fun LazyListScope.homeTransactionsComponent(
    state: TransactionsState,
) {

    when (state) {
        is TransactionSuccess -> transactionsSuccessComponent(state)
        is TransactionLoading -> transactionLoadingComponent()
        is TransactionError -> transactionErrorComponent()
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
                    textAlign = TextAlign.Center,
                    style = UiKitTypography.TextMD.Regular
                )
            }
        }
    } else {

        items(
            items = state.list,
        ) { data ->
            val dismissState = data.dismissState

            LaunchedEffect(data.isWaiting) {
                if (!data.isWaiting) {
                    dismissState.reset()
                }
            }
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> Color.Transparent
                            DismissValue.DismissedToEnd -> UiKitColors.colors.feedbackGreen.`500`
                            DismissValue.DismissedToStart -> UiKitColors.colors.feedbackRed.`500`
                            else -> UiKitColors.colors.primary.`100`
                        },
                        label = "color"
                    )
                    val alignment = Alignment.CenterEnd

                    val scale by animateFloatAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> 0.25f
                            else -> 1f
                        },
                        label = "scale"
                    )

                    val alphaDeleteIcon by animateFloatAsState(
                        when (dismissState.targetValue) {
                            DismissValue.DismissedToStart -> 1f
                            else -> 0f
                        },
                        label = "alphaDeleteIcon"
                    )
                    val alphaUpdateIcon by animateFloatAsState(
                        when (dismissState.targetValue) {
                            DismissValue.DismissedToEnd -> 1f
                            else -> 0f
                        },
                        label = "alphaUpdateIcon"
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
                                .padding(horizontal = padding)
                                .alpha(alphaUpdateIcon)
                                .align(Alignment.CenterStart)
                        )

                        Icon(
                            Delete,
                            contentDescription = "Delete Icon",
                            modifier = Modifier
                                .scale(scale)
                                .padding(horizontal = padding)
                                .alpha(alphaDeleteIcon)
                                .align(Alignment.CenterEnd)
                        )
                        if (data.isWaiting) {
                            CircularProgressIndicator(
                                Modifier
                                    .padding(horizontal = doublePadding)
                                    .align(Alignment.CenterEnd)
                            )
                        }
                    }
                },
                dismissContent = {
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(vertical = halfPadding)
                            .padding(end = padding)
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
                .padding(horizontal = padding, vertical = halfPadding)
                .size(32.dp)
                .clip(RoundedCornerShape(16.dp))
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
                .padding(end = padding, start = 10.dp)
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
                        horizontal = padding, vertical = 30.dp
                    ),
                style = UiKitTypography.TextMD.Regular
            )
        }
    }
}