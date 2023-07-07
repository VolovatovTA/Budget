package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.features.create_update_delete_transactions.R
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.padding
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.corner
import ru.bysoft.android.budget.uikit.styles.halfPadding

val HEIGHT_WALLET = 90.dp
val WIDTH_WALLET = 170.dp

@Composable
fun WalletChooserComponent(
    state: IWalletFieldState,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    when (state) {
        is WalletWaitingState -> WalletWaitingComponent()
        is WalletErrorState -> WalletErrorComponent()
        is WalletSuccessState -> WalletSuccessComponent(modifier = modifier, state, onClick)
    }
}

@Composable
private fun WalletSuccessComponent(
    modifier: Modifier = Modifier,
    state: WalletSuccessState,
    onClick: (String) -> Unit = {}
) {
    val countRows = if(state.list.size > 4) 2 else 1
    LazyHorizontalGrid(
        rows = GridCells.Fixed(countRows),
        modifier = modifier.height(countRows * HEIGHT_WALLET + padding * (countRows - 1)),
        verticalArrangement = Arrangement.spacedBy(padding),
        horizontalArrangement = Arrangement.spacedBy(padding),
        contentPadding = PaddingValues(start = padding, end = padding, bottom = halfPadding)
    ) {
        items(
            count = state.list.size,
            itemContent = { index ->
                val backgroundColor = UiKitColors.card(state.list[index].id == state.selectedWalletId)
                Surface(
                    shape = RoundedCornerShape(corner),
                    color = backgroundColor,
                    modifier = Modifier
                        .size(WIDTH_WALLET, HEIGHT_WALLET),
                    elevation = 3.dp
                ) {
                    Column(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onClick(state.list[index].id) }
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(halfPadding),
                    ) {
                        Spacer(modifier = Modifier.height(halfPadding))

                        Text(
                            text = state.list[index].name,
                            style = UiKitTypography.TextMD.Regular,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = halfPadding)
                                .weight(1f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = state.list[index].balance,
                            style = UiKitTypography.TextMD.Regular,
                            modifier = Modifier.padding(horizontal = halfPadding),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(halfPadding))
                    }
                }
            }
        )
    }
}

@Composable
private fun WalletWaitingComponent() {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(countRows),
        modifier = Modifier.height(countRows * HEIGHT_WALLET + padding * (countRows - 1)),
        verticalArrangement = Arrangement.spacedBy(padding),
        horizontalArrangement = Arrangement.spacedBy(padding),
        contentPadding = PaddingValues(start = padding, end = padding, bottom = halfPadding)
    ) {
        items(5) {
            UiKitShimmerComponent(
                Modifier
                    .fillMaxWidth()
                    .size(WIDTH_WALLET, HEIGHT_WALLET)
            )
        }
    }
}

@Composable
private fun WalletErrorComponent() {
    Text(
        text = stringResource(R.string.error_loading_wallets),
        style = UiKitTypography.TextMD.Regular,
        color = UiKitColors.colors.type.high,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding)
    )
}

@Preview
@Composable
fun WalletChooserComponentPreview() {
    Column {
        WalletChooserComponent(WalletWaitingState, onClick = {})
        WalletChooserComponent(WalletErrorState, onClick = {})
        WalletChooserComponent(WalletSuccessState(
            listOf(
                WalletInfo(
                    "First",
                    "1234.5 ₽",
                    "",
                    BudgetCurrencyEnum.USD
                ),
                WalletInfo(
                    "Second",
                    "1234.5 ₽",
                    "",
                    BudgetCurrencyEnum.RUB
                ),
                WalletInfo(
                    "Third",
                    "1234.5 ₽",
                    "",
                    BudgetCurrencyEnum.BGN
                ),
            )
        ), onClick = {})
    }
}
