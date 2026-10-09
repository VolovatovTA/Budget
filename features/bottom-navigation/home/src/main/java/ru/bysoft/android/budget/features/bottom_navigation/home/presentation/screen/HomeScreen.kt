package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import ru.bysoft.android.budget.features.bottom_navigation.home.IHomeViewModel
import ru.bysoft.android.budget.features.bottom_navigation.home.R
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.DialogInfo
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeLoadingState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletsLoadingState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.filters.HomeFiltersComponent
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.title.HomeTitleComponent
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.transactions.homeTransactionsComponent
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.wallets.WalletsPagerComponent
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.corner
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding

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
    val dialogState = viewModel.dialogState.collectAsState().value

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
        if (dialogState != null) {
            UiKitDialog(viewModel::onDialogDismiss,viewModel::onDialogConfirmed, dialogState)
        }
        Column(
            modifier = Modifier
                .pullRefresh(
                    pullRefreshState,
                    enabled = refreshEnabled
                )
                .padding(it)
                .fillMaxSize()
                .background(Color.Transparent)

        ) {
            Box(
                Modifier
            ) {
                Column {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(halfPadding),
                    ) {
                        item {
                            Column {
                                HomeTitleComponent(
                                    meState,
                                    viewModel::onSettingsClick,
                                    viewModel::onMainCurrencyChanged
                                )
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
                            onTransactionClick = viewModel::openTransaction,
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

@Preview
@Composable
fun UiKitDialogPreview() {
    val dialogInfo = DialogInfo(
        title = R.string.dialog_delete_transaction_title,
        message = R.string.dialog_delete_transaction_subtitle,
        positiveButtonText = R.string.dialog_delete_transaction_positive_button,
        negativeButtonText = R.string.dialog_delete_transaction_negative_button,
        data = ""
    )
    UiKitDialog(
        {},
        {},
        dialogState = dialogInfo
    )
}

@Composable
private fun <T> UiKitDialog(
    onDialogDismiss: (T) -> Unit,
    onDialogConfirmed: (T) -> Unit,
    dialogState: DialogInfo<T>
) {
    Dialog(onDismissRequest = { onDialogDismiss(dialogState.data) }) {
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(corner))
                .background(UiKitColors.colors.surface.primary)
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(padding)
        ) {
            Text(
                text = stringResource(id = dialogState.title),
                style = UiKitTypography.TextLG.Bold
            )
            Text(
                text = stringResource(id = dialogState.message),
                style = UiKitTypography.TextMD.Regular
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(padding),
                modifier = Modifier.align(Alignment.End)
            ) {
                UiKitButton(
                    info = UiKitButtonInfo(
                        text = stringResource(id = dialogState.negativeButtonText)
                    ),
                    onClick = { onDialogDismiss(dialogState.data) },
                )
                UiKitButton(
                    info = UiKitButtonInfo(
                        type = ButtonType.TERTIARY,
                        text = stringResource(id = dialogState.positiveButtonText)
                    ),
                    onClick = { onDialogConfirmed(dialogState.data) }
                )
            }
        }
    }
}
