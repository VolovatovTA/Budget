package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.common.util.BudgetCurrency
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.HEIGHT_ELEMENT
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.MAX_HEIGHT
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.features.create_update_delete_transactions.R
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.padding


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

@Preview
@Composable
private fun WalletSuccessComponent(
    state: WalletSuccessState = WalletSuccessState(
        listOf(
            WalletInfo(
                "dfvhjkhbkvksjhgblasdhbglkadshfbglkdfbglkdjfgbnjhbsfv",
                "1234.5 ₽",
                "",
                BudgetCurrency("₽", "RUB")
            ),
        )
    ), onClick: (String) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .padding(horizontal = 5.dp)
            .heightIn(max = MAX_HEIGHT)
            .verticalScroll(scrollState)
    ) {
        state.list.forEach { wallet ->
            val backgroundColor =
                if (wallet.id == state.selectedWalletId) UiKitColors.colors.neutral.`300`
                else UiKitColors.colors.surface.primary

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HEIGHT_ELEMENT - 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundColor)
                    .border(0.1.dp, UiKitColors.colors.primary.`1100`, RoundedCornerShape(10.dp))
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
                    style = UiKitTypography.TextMD.Regular,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = padding, end = padding / 2),
                    maxLines = 2
                )
                Text(
                    text = wallet.balance,
                    style = UiKitTypography.TextMD.Regular,
                    modifier = Modifier.padding(end = padding),
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
            .padding(horizontal = padding / 2)
            .verticalScroll(rememberScrollState())
    ) {
        (0..1).forEach { _ ->
            Spacer(modifier = Modifier.height(10.dp))
            UiKitShimmerComponent(
                Modifier
                    .fillMaxWidth()
                    .height(HEIGHT_ELEMENT - 10.dp)
            )
        }
    }
}

@Composable
private fun WalletErrorComponent() {
    Text(
        text = stringResource(R.string.error_loading_wallets),
        style = UiKitTypography.TextMD.Regular,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 5.dp)
    )
}
