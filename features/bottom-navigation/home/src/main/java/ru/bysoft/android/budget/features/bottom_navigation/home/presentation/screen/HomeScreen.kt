package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.pager.*
import ru.bysoft.android.budget.features.bottom_navigation.home.IHomeViewModel
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeLoadingState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletsLoadingState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.filters.HomeFiltersComponent
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.title.HomeTitleComponent
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.transactions.*
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.wallets.WalletsPagerComponent
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.icons.pack.*
import ru.bysoft.android.budget.uikit.styles.halfPadding
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: IHomeViewModel,
) {
    LaunchedEffect(Unit) { viewModel.loadData() }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.toastState.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    val walletsState = viewModel.walletsState.collectAsState().value
    val meState = viewModel.meState.collectAsState().value
    val filtersState = viewModel.filterState.collectAsState().value
    val transactionsState = viewModel.transactionsState.collectAsState().value

    val refreshingWallets = walletsState as? WalletsLoadingState
    val refreshEnabled = walletsState !is WalletsLoadingState && meState !is MeLoadingState

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshingWallets?.isRefreshing ?: false,
        onRefresh = {
            viewModel.loadData(true)
        }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        backgroundColor = UiKitColors.colors.surface.primary,
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
                Column {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(halfPadding),
                    ) {
                        item {
                            Column {
                                HomeTitleComponent(meState, viewModel::onSettingsClick, viewModel::onMainCurrencyChanged)
                                Spacer(modifier = Modifier.height(halfPadding))
                            }
                        }
                        item {
                            Column {
                                WalletsPagerComponent(
                                    walletsState,
                                    viewModel::onPositionSelected,
                                    viewModel::onClickCreateWallet,
                                    viewModel::onClickEditWallet,
                                )
                                Spacer(modifier = Modifier.height(halfPadding))
                            }
                        }
                        item {
                            HomeFiltersComponent(filtersState, viewModel::onClickFilter)
                        }
                        homeTransactionsComponent(
                            transactionsState,
                        )
                    }
                }
                PullRefreshIndicator(
                    refreshingWallets?.isRefreshing ?: false,
                    pullRefreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}
