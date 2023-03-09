package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import ru.bysoft.android.budget.features.bottom_navigation.home.IHomeViewModel
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeLoadingState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletsLoadingState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.title.HomeTitleComponent
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.transactions.HomeTransactionsComponent
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.wallets.WalletsPagerComponent
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.filters.HomeFiltersComponent
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.icons.pack.*
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: IHomeViewModel,
) {
    LaunchedEffect(Unit) { viewModel.loadData() }

    val toastState = viewModel.toastState.collectAsState().value

    val context = LocalContext.current
    LaunchedEffect(toastState?.keyLaunchedEffect) {
        if (toastState != null) Toast.makeText(context, toastState.text, Toast.LENGTH_SHORT).show()
    }
    val walletsState = viewModel.walletsState.collectAsState().value
    val meState = viewModel.meState.collectAsState().value
    val filtersState = viewModel.filterState.collectAsState().value
    val transactionsState = viewModel.transactionsState.collectAsState().value

    val refreshingWallets = walletsState as? WalletsLoadingState
    val refreshEnabled = walletsState !is WalletsLoadingState && meState !is MeLoadingState

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshingWallets?.isRefreshing ?: false,
        onRefresh = { viewModel.loadData(true) }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.Transparent,
        bottomBar = {}
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Transparent)

        ) {
            Box(
                Modifier.pullRefresh(
                    pullRefreshState,
                    enabled = refreshEnabled
                )
            ) {
                LazyColumn(
                    Modifier
                        .bottomElevation()

                ) {
                    item {
                        HomeTitleComponent(meState)

                        WalletsPagerComponent(
                            walletsState,
                            viewModel::onClickSimpleWallet,
                            viewModel::onClickCreateWallet,
                            viewModel::onClickEditWallet,
                            viewModel::onPositionChanged
                        )

                        HomeFiltersComponent(filtersState, viewModel::onClickFilter)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(UiKitColors.colors.dark40)
                        )
                    }
                }
                PullRefreshIndicator(
                    refreshingWallets?.isRefreshing ?: false,
                    pullRefreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }

            HomeTransactionsComponent(
                transactionsState,
                onRefresh = viewModel::loadTransactions,
            )
        }
    }
}

private fun Modifier.bottomElevation(elevation: Dp = 8.dp): Modifier =
    this.then(Modifier.drawWithContent {
        val paddingPx = elevation.toPx()
        clipRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = size.height + paddingPx
        ) {
            this@drawWithContent.drawContent()
        }
    })