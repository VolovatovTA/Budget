package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.common.util.BudgetCurrency
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.HEIGHT_ELEMENT
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.MAX_HEIGHT
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.styles.UiKitStyles
import ru.bysoft.android.budget.features.create_update_delete_transactions.R

@Composable
fun WalletChooserComponent(
    state: IWalletFieldState,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    Box(modifier = modifier) {
        when (state) {
            is WalletWaitingState -> WalletWaitingComponent()
            is WalletErrorState -> WalletErrorComponent()
            is WalletSuccessState -> WalletSuccessComponent(state, onClick)
        }
    }
}

//@Preview
@Composable
private fun WalletSuccessComponent(state: WalletSuccessState = WalletSuccessState(listOf(
    WalletInfo("dfvhjkhbkvksjhgblasdhbglkadshfbglkdfbglkdjfgbnjhbsfv", "1234.5", "", BudgetCurrency("₽", "RUB")),
)), onClick: (String) -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .padding(horizontal = 5.dp)
            .heightIn(max = MAX_HEIGHT)
            .verticalScroll(scrollState)
    ) {
        state.list.forEach { wallet ->
            val backgroundColor =
                if (wallet.id == state.selectedWalletId) UiKitColors.colors.col4_inactive
                else UiKitColors.colors.light

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HEIGHT_ELEMENT)
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundColor)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onClick(wallet.id) }
                    )
                    .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = wallet.name,
                    style = UiKitStyles.Body2,
                    modifier = Modifier.weight(1f).padding(start = 10.dp, end = 5.dp),
                    maxLines = 2
                )
                Text(
                    text = wallet.balance,
                    style = UiKitStyles.Body2,
                    modifier = Modifier.padding(end = 10.dp),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun WalletWaitingComponent() {
    Column(
        modifier = Modifier
            .heightIn(max = MAX_HEIGHT)
            .padding(horizontal = 5.dp)
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        (0..4).forEach { _ ->
            UiKitShimmerComponent(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .height(HEIGHT_ELEMENT)
            )
        }
    }
}

@Composable
private fun WalletErrorComponent() {
    Text(
        text = stringResource(R.string.error_loading_wallets),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 5.dp)
    )
}
