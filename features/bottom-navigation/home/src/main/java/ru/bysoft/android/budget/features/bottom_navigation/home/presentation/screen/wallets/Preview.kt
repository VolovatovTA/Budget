package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.wallets

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletCardPresentation
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletCreateNewPresentation
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletsSuccessState

@Preview
@Composable
fun SuccessPreview() {
    SuccessWallets(
        state = WalletsSuccessState(
            listOf(
                WalletCardPresentation(
                    balance = 300000000f,
                    name = "Очень интересное и длинное название для кошелька",
                    walletId = "sldjnvds",
                    currency = BudgetCurrencyEnum.BGN,
                    backgroundColor = "primary.500",
                    lastOperationDate = "April 22, 2022",
                    icon = "wallet"
                ),
                WalletCardPresentation(
                    balance = 300f,
                    name = "kjbnsv",
                    walletId = "sldjnvds",
                    currency = BudgetCurrencyEnum.KZT,
                    backgroundColor = "primary.500",
                    lastOperationDate = "April 22, 2022",
                    icon = "wallet"
                ),
                WalletCreateNewPresentation
            ),""
        ),
        lazyListState = rememberLazyListState(3),
        {},
        {},
        {},
    )
    LoadingWallets(rememberLazyListState())

}

@Preview
@Composable
fun LoadingPreview() {
    LoadingWallets(rememberLazyListState())
}

@Preview
@Composable
fun ErrorPreview() {
    ErrorWallets()
}