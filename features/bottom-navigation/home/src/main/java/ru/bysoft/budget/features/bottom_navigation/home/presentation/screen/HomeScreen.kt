package ru.bysoft.budget.features.bottom_navigation.home.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import ru.bysoft.budget.features.bottom_navigation.home.IHomeViewModel
import ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.title.HomeTitleComponent
import ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.transactions.HomeTransactionsComponent
import ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.wallets.WalletsPagerComponent
import ru.bysoft.budget.home.presentation.screen.transactions.HomeFiltersComponent
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.icons.pack.*
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: IHomeViewModel,
    controller: NavHostController
) {
    LaunchedEffect(Unit) { viewModel.init(controller) }

    val walletsState = viewModel.walletsState.collectAsState().value
    val meState = viewModel.meState.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(), backgroundColor = UiKitColors.colors.white,
        bottomBar = {}
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(UiKitColors.colors.white)
        ) {
            Column(Modifier.shadow(2.dp)) {

                HomeTitleComponent(meState)

                WalletsPagerComponent(walletsState, viewModel::onClickSimpleWallet, viewModel::onClickCreateWallet,viewModel::onClickEditWallet)

                HomeFiltersComponent()
            }

            HomeTransactionsComponent()

        }
    }
}