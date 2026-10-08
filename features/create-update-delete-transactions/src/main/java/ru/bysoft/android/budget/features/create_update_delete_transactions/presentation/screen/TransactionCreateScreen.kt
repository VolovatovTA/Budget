package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.common.util.TransactionTypeEnum
import ru.bysoft.android.budget.features.create_update_delete_transactions.R
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components.CategoryChooserComponent
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components.ExchangesComponent
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components.ShortSuccessCategoryComponent
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components.WalletChooserComponent
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.ITransactionCreateViewModel
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.ITransactionsViewModel
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.TransactionCreateViewModel
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.TransactionUpdateViewModel
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.rowtab.UiKitRowTab
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitRowTabState
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitTabInfo
import ru.bysoft.android.budget.uikit.components.textfield.UiKitCurrencyPopUpTextField
import ru.bysoft.android.budget.uikit.components.textfield.UiKitTextField
import ru.bysoft.android.budget.uikit.components.textfield.UiKitTextFieldWithCurrency
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.templates.topBar
import ru.bysoft.android.budget.uikit.utils.ExpandVertically

internal val HEIGHT_ELEMENT = 60.dp
internal val padding = 12.dp

@Composable
fun TransactionScreen(
    viewModel: ITransactionsViewModel,
) {
    val transactionState by viewModel.state.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.toastState.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        backgroundColor = Color.Transparent,
        topBar = topBar(R.string.create_screen_title, viewModel::back),
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxSize()
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues),
        ) {
            if (viewModel is ITransactionCreateViewModel) {
                TransactionCreateScreenMain(
                    modifier = Modifier.fillMaxSize(),
                    transactionState = transactionState,
                    setCategoriesIds = viewModel::setCategoriesIds,
                    setWalletId = viewModel::setWalletId,
                    setComment = viewModel::setComment,
                    setAmount = viewModel::setAmount,
                    setCurrency = viewModel::setCurrency,
                    setExchangeAmount = viewModel::setExchangeAmount,
                    setTransactionType = viewModel::setTypeTransactions,
                    onEmptyCategoryClick = viewModel::onEmptyCategoryClick,
                    setFullAmount = viewModel::setFullAmount,
                    setRevert = viewModel::setRevert,
                )
            }
        }
        val isCategoryLoadedAndNotEmptyAndSelectedAtLeastOne =
            (transactionState.categoryState as? CategorySuccess)?.listCategory?.any { it.isChosen }
                ?: false
        val isWalletToLoadedAndNotEmptyAndSelectedAtLeastOne =
            (transactionState.walletToFieldState as? WalletSuccessState)?.selectedWalletId != null
        val isWalletFromLoadedAndNotEmptyAndSelectedAtLeastOne =
            (transactionState.walletFromFieldState as? WalletSuccessState)?.selectedWalletId != null
        val isButtonEnabled = when (transactionState) {
            is TransactionExpenseState -> isCategoryLoadedAndNotEmptyAndSelectedAtLeastOne && isWalletFromLoadedAndNotEmptyAndSelectedAtLeastOne
            is TransactionIncomeState -> isCategoryLoadedAndNotEmptyAndSelectedAtLeastOne && isWalletToLoadedAndNotEmptyAndSelectedAtLeastOne
            is TransactionTransferState -> isWalletFromLoadedAndNotEmptyAndSelectedAtLeastOne && isWalletToLoadedAndNotEmptyAndSelectedAtLeastOne
        }
        TransactionButtonComponent(
            enabled = isButtonEnabled, onClick = {
                when (viewModel) {
                    is TransactionCreateViewModel -> viewModel.create()
                    is TransactionUpdateViewModel -> viewModel.update()
                }
            }, modifier = Modifier
                .fillMaxSize()
                .imePadding()
        )
        if (transactionState.isLoading) {
            Dialog(
                onDismissRequest = { }, properties = DialogProperties(
                    dismissOnBackPress = false, dismissOnClickOutside = false
                )
            ) {
                CircularProgressIndicator(
                    color = UiKitColors.colors.feedbackGreen.`600`,
                )
            }
        }
    }
}


@Composable
private fun TransactionCreateScreenMain(
    transactionState: ITransactionState,
    setCategoriesIds: (CategoryPresentation) -> Unit,
    setWalletId: (fromId: String?, toId: String?) -> Unit,
    setComment: (String) -> Unit,
    setAmount: (String) -> Unit,
    setCurrency: (BudgetCurrencyEnum) -> Unit,
    setExchangeAmount: (BudgetCurrencyEnum, String) -> Unit,
    setTransactionType: (TransactionTypeEnum) -> Unit,
    onEmptyCategoryClick: () -> Unit,
    setFullAmount: (BudgetCurrencyEnum, Boolean) -> Unit,
    setRevert: (BudgetCurrencyEnum, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }
    val rowTabs = TransactionTypeEnum.values()
    val selectedType = when (transactionState) {
        is TransactionIncomeState -> TransactionTypeEnum.INCOME
        is TransactionExpenseState -> TransactionTypeEnum.EXPENSE
        is TransactionTransferState -> TransactionTypeEnum.TRANSFER
    }

    LazyColumn(
        modifier
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(padding),
    ) {
        item {
            Spacer(modifier = Modifier.height(halfPadding))
        }
        item {
            UiKitRowTab(startState = UiKitRowTabState(rowTabs.map {
                UiKitTabInfo(
                    text = it.text,
                    isChecked = it == selectedType,
                    isEnabled = true
                )
            }), onCheckChanged = { _, position ->
                setTransactionType(rowTabs[position])
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding)
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                UiKitTextFieldWithCurrency(state = transactionState.amountState,
                    onValueChange = setAmount,
                    label = stringResource(R.string.text_field_amount_label),
                    inputType = KeyboardType.Number,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = padding)
                        .focusRequester(focusRequester),
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                    popUpList = transactionState.currencyFieldState,
                    popUpItem = { t ->
                        t?.let {
                            UiKitCurrencyPopUpTextField(t.displayName, t.flag)
                        }
                    },
                    onSelectPopUpItem = {
                        setCurrency(it)
                    })
            }
        }

        item {
            UiKitTextField(state = transactionState.commentState,
                onValueChange = setComment,
                label = stringResource(R.string.text_field_comment_label),
                inputType = KeyboardType.Text,
                modifier = Modifier
                    .padding(horizontal = padding)
                    .fillMaxWidth(),
                keyboardActions = KeyboardActions {
                    if (transactionState.exchangeFieldState.isEmpty()) {
                        focusManager.clearFocus()
                    } else {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                })
        }

        item {
            Column {
                ExpandVertically(transactionState.walletFromFieldState != null) {
                    transactionState.walletFromFieldState?.let { iWalletFieldState ->
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.spacedBy(padding)
                        ) {
                            Text(
                                stringResource(R.string.wallet_from_title_text),
                                style = UiKitTypography.TextMD.SemiBold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = padding)
                            )
                            WalletChooserComponent(
                                iWalletFieldState
                            ) { fromId -> setWalletId(fromId, null) }
                        }

                    }
                }
                ExpandVertically(transactionState.walletToFieldState != null) {
                    transactionState.walletToFieldState?.let { iWalletFieldState ->
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.spacedBy(padding)
                        ) {
                            Text(
                                stringResource(R.string.wallet_to_text),
                                style = UiKitTypography.TextMD.SemiBold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = padding)
                            )
                            WalletChooserComponent(
                                iWalletFieldState
                            ) { toId -> setWalletId(null, toId) }
                        }
                    }
                }
            }
        }

        item {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(padding)
            ) {
                when (transactionState) {
                    is TransactionIncomeState -> {
                        Text(
                            stringResource(R.string.title_categories_incomes),
                            style = UiKitTypography.TextMD.SemiBold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = padding)
                        )
                        CategoryChooserComponent(
                            transactionState, setCategoriesIds, onEmptyCategoryClick
                        )
                        if (transactionState.categoryState is CategorySuccess && transactionState.categoryState.listCategory.isEmpty()) {
                            ShortSuccessCategoryComponent(
                                stringResource(R.string.no_categories_income), onEmptyCategoryClick
                            )
                        }
                    }
                    is TransactionExpenseState -> {
                        Text(
                            stringResource(R.string.title_categories_expense),
                            style = UiKitTypography.TextMD.SemiBold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = padding)
                        )
                        CategoryChooserComponent(
                            transactionState, setCategoriesIds, onEmptyCategoryClick
                        )
                        if (transactionState.categoryState is CategorySuccess && transactionState.categoryState.listCategory.isEmpty()) {
                            ShortSuccessCategoryComponent(
                                stringResource(R.string.no_categories_expense), onEmptyCategoryClick
                            )
                        }
                    }
                    is TransactionTransferState -> {}
                }
            }
        }

        item {
            ExchangesComponent(
                modifier = Modifier,
                exchangeFieldState = transactionState.exchangeFieldState,
                setAmount = setExchangeAmount,
                focusManager = focusManager,
                setFullAmount = setFullAmount,
                setRevert = setRevert
            )
        }

        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
private fun TransactionButtonComponent(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.background(Color.Transparent), contentAlignment = Alignment.BottomCenter
    ) {
        UiKitButton(
            info = UiKitButtonInfo(
                text = stringResource(R.string.btn_text_create), size = ButtonSize.MEDIUM
            ),
            onClick = onClick,
            modifier = Modifier.padding(bottom = padding),
            isButtonEnabled = enabled
        )
    }


}