package ru.bysoft.android.budget.features.transaction_detail.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding
import ru.bysoft.android.budget.uikit.templates.topBar
import ru.bysoft.android.budget.features.transaction_detail.R
import ru.bysoft.android.budget.features.transaction_detail.presentation.ITransactionDetailViewModel
import ru.bysoft.android.budget.features.transaction_detail.presentation.entity.CategoryItem
import ru.bysoft.android.budget.features.transaction_detail.presentation.entity.TransactionDetailState

@Composable
fun TransactionDetailScreen(viewModel: ITransactionDetailViewModel) {
    val state = viewModel.state.collectAsState().value
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        backgroundColor = UiKitColors.colors.surface.primary,
        topBar = topBar(
            title = R.string.transaction_detail_title,
            onBackClick = viewModel::back,
            onClickDelete = viewModel::delete.takeIf { state is TransactionDetailState.Success },
        ),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                TransactionDetailState.Loading -> CircularProgressIndicator(color = UiKitColors.colors.primary.`300`)
                TransactionDetailState.NotFound -> Message(R.string.transaction_detail_not_found)
                TransactionDetailState.Error -> Message(R.string.transaction_detail_error)
                is TransactionDetailState.Success -> Details(state)
            }
        }
    }
}

@Composable
private fun Message(text: Int) {
    Text(
        text = stringResource(text),
        style = UiKitTypography.TextMD.Regular,
        color = UiKitColors.colors.type.high,
        modifier = Modifier.padding(padding),
    )
}

@Composable
private fun Details(state: TransactionDetailState.Success) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(padding),
    ) {
        Text(
            text = state.amount,
            style = UiKitTypography.DisplayXS.Regular,
            color = UiKitColors.colors.type.high,
            modifier = Modifier.fillMaxWidth(),
        )
        Field(R.string.transaction_detail_type, stringResource(state.typeText))
        state.date?.let { Field(R.string.transaction_detail_date, it) }
        state.comment?.let { Field(R.string.transaction_detail_comment, it) }
        if (state.categories.isNotEmpty()) {
            Text(
                text = stringResource(R.string.transaction_detail_categories),
                style = UiKitTypography.TextXS.Regular,
                color = UiKitColors.colors.type.medium,
            )
            state.categories.forEach { Category(it) }
        }
        if (state.isDeleting) {
            CircularProgressIndicator(
                color = UiKitColors.colors.primary.`300`,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Composable
private fun Field(label: Int, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(label),
            style = UiKitTypography.TextXS.Regular,
            color = UiKitColors.colors.type.medium,
            modifier = Modifier.align(Alignment.CenterVertically),
        )
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            Text(
                text = value,
                style = UiKitTypography.TextMD.Regular,
                color = UiKitColors.colors.type.high,
                modifier = Modifier.padding(start = halfPadding),
            )
        }
    }
}

@Composable
private fun Category(item: CategoryItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(halfPadding),
    ) {
        item.icon?.let {
            Icon(
                painter = painterResource(it),
                contentDescription = null,
                tint = UiKitColors.colors.type.high,
                modifier = Modifier.size(24.dp),
            )
        }
        Text(
            text = item.name,
            style = UiKitTypography.TextMD.Regular,
            color = UiKitColors.colors.type.high,
        )
    }
}
