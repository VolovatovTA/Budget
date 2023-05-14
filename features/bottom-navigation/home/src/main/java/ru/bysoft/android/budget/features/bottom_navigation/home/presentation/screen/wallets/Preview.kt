package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.wallets

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletCardPresentation
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletsSuccessState

@Preview
@OptIn(ExperimentalPagerApi::class)
@Composable
fun SuccessPreview() {
    SuccessWallets(
        state = WalletsSuccessState(
            listOf(
                WalletCardPresentation(
                    balance = 300000000f,
                    name = "Очень интересное и длинное название для кошелька",
                    walletId = "sldjnvds",
                    currency = "USD",
                    backgroundColor = "col3"
                ),
                WalletCardPresentation(
                    balance = 300f,
                    name = "kjbnsv",
                    walletId = "sldjnvds",
                    currency = "USD",
                    backgroundColor = "col3"
                ),
            )
        ),
        pagerState = rememberPagerState(),
        {},
        {},
        {},
        {}
    )
}

@Preview
@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoadingPreview() {
    LoadingWallets(rememberPagerState())
}

@Preview
@Composable
fun ErrorPreview() {
    ErrorWallets()
}