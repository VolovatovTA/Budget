package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.common.util.BudgetCurrency
import ru.bysoft.android.budget.common.util.TransactionTypeEnum
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components.CategoryChooserComponent
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components.ExchangesComponent
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components.WalletChooserComponent
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.ITransactionsViewModel
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.TransactionCreateViewModel
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.currencyfield.UiKitCurrencyPopUp
import ru.bysoft.android.budget.uikit.components.rowtab.UiKitRowTab
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitTabInfo
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitRowTabState
import ru.bysoft.android.budget.uikit.components.textfield.UiKitTextField
import ru.bysoft.android.budget.uikit.icons.pack.ArrowLeft
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.features.create_update_delete_transactions.R
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components.ShortSuccessCategoryComponent

internal val HEIGHT_ELEMENT = 60.dp
internal val MAX_HEIGHT = 200.dp
internal val padding = 12.dp

@Composable
fun TransactionScreen(
    viewModel: ITransactionsViewModel,
) {
    val transactionState by viewModel.state.collectAsState()
    transactionState.toastText?.let {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    Scaffold(
        backgroundColor = UiKitColors.colors.surface.primary,
        topBar = transactionTopBar(viewModel)
    ) { paddingValues ->
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (viewModel is TransactionCreateViewModel) {
                TransactionCreateScreenMain(
                    modifier = Modifier.weight(10f),
                    paddingValues = paddingValues,
                    transactionState = transactionState,
                    setCategoriesIds = viewModel::setCategoriesIds,
                    setWalletId = viewModel::setWalletId,
                    setComment = viewModel::setComment,
                    setAmount = viewModel::setAmount,
                    setCurrency = viewModel::setCurrency,
                    setExchangeAmount = viewModel::setExchangeAmount,
                    setTransactionType = viewModel::setTypeTransactions,
                    onEmptyCategoryClick = viewModel::onEmptyCategoryClick,
                )

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
                    isLoading = transactionState.isLoading,
                    enabled = isButtonEnabled,
                    onClick = viewModel::create,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


@Composable
@Preview(
    apiLevel = 26,
    widthDp = 540,
    heightDp = 1170,
    name = "TransactionScreen",
    group = "Transaction",
    locale = "Ru",
    fontScale = 1f,
    showBackground = true,
    showSystemUi = false,
    backgroundColor = 0L,
    uiMode = UI_MODE_TYPE_NORMAL,
)
private fun TransactionCreateScreenMain(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    transactionState: ITransactionState = TransactionTransferState(
        walletFromFieldState = WalletSuccessState(
            list = listOf(
                WalletInfo(
                    "1",
                    "1",
                    "1",
                    BudgetCurrency.Unkcnown
                ),
                WalletInfo(
                    "1",
                    "1",
                    "1",
                    BudgetCurrency.Unkcnown
                )
            )
        ),
    ),
    setCategoriesIds: (CategoryPresentation) -> Unit = {},
    setWalletId: (fromId: String?, toId: String?) -> Unit = { _, _ -> },
    setComment: (String) -> Unit = {},
    setAmount: (String) -> Unit = {},
    setCurrency: (BudgetCurrency) -> Unit = {},
    onButtonClick: () -> Unit = {},
    setExchangeAmount: (BudgetCurrency, String) -> Unit = { _, _ -> },
    setTransactionType: (TransactionTypeEnum) -> Unit = { },
    onEmptyCategoryClick: () -> Unit = {},
) {
    Column(
        modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
        val rowTabs = listOf(
            TransactionTypeEnum.EXPENSE,
            TransactionTypeEnum.TRANSFER,
            TransactionTypeEnum.INCOME,
        )
        val selectedType = when (transactionState) {
            is TransactionIncomeState -> TransactionTypeEnum.INCOME
            is TransactionExpenseState -> TransactionTypeEnum.EXPENSE
            is TransactionTransferState -> TransactionTypeEnum.TRANSFER
        }
        UiKitRowTab(
            startState = UiKitRowTabState(
                rowTabs.map {
                    UiKitTabInfo(
                        info = UiKitButtonInfo(
                            stringResource(it.text),
                            size = ButtonSize.SMALL
                        ),
                        isChecked = it == selectedType,
                        isEnabled = true
                    )
                }
            ),
            onCheckChanged = { _, position ->
                setTransactionType(rowTabs[position])
            }
        )

        Row(
            Modifier
                .padding(horizontal = padding / 2)
                .animateContentSize()
        ) {
            transactionState.walletFromFieldState?.let { iWalletFieldState ->
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.wallet_from_title_text),
                        style = UiKitTypography.TextMD.Regular,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(
                                top = padding,
                                start = padding / 2,
                                end = padding / 2,
                                bottom = padding
                            )
                            .fillMaxWidth()
                    )
                    WalletChooserComponent(
                        iWalletFieldState
                    ) { fromId -> setWalletId(fromId, null) }
                }

            }
            transactionState.walletToFieldState?.let { iWalletFieldState ->
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.wallet_to_text),
                        style = UiKitTypography.TextMD.Regular,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(
                                top = padding,
                                start = padding / 2,
                                end = padding / 2,
                                bottom = padding
                            )
                            .fillMaxWidth()
                    )
                    WalletChooserComponent(
                        iWalletFieldState
                    ) { toId -> setWalletId(null, toId) }
                }
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UiKitTextField(
                state = transactionState.amountState,
                onValueChange = setAmount,
                label = stringResource(R.string.text_field_amount_label),
                inputType = KeyboardType.Number,
                modifier = Modifier
                    .padding(start = padding, top = padding / 2, end = padding / 2)
                    .weight(1f)
                    .height(HEIGHT_ELEMENT)
                    .focusRequester(focusRequester),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
            )
            UiKitCurrencyPopUp(
                info = transactionState.currencyFieldState,
                onNameChanged = setCurrency,
                modifier = Modifier
                    .padding(
                        end = padding,
                        top = padding + 8.dp,
                        bottom = padding / 2,
                        start = padding / 2
                    )
                    .weight(1f)
                    .height(HEIGHT_ELEMENT - 8.dp)
            )
        }
        UiKitTextField(
            state = transactionState.commentState,
            onValueChange = setComment,
            label = stringResource(R.string.text_field_comment_label),
            inputType = KeyboardType.Text,
            modifier = Modifier
                .padding(horizontal = padding, vertical = padding / 2)
                .height(HEIGHT_ELEMENT),
            keyboardActions = KeyboardActions {
                focusManager.moveFocus(FocusDirection.Next)
            }
        )

        ExchangesComponent(
            transactionState.exchangeFieldState,
            setAmount = setExchangeAmount,
            mainCurrency = transactionState.currencyFieldState.selectedCurrency,
            focusManager = focusManager,
        )

        Column(modifier = Modifier.animateContentSize()) {
            when (transactionState) {
                is TransactionIncomeState -> {
                    Text(
                        stringResource(R.string.title_categories_incomes),
                        style = UiKitTypography.TextMD.Regular,
                        modifier = Modifier
                            .padding(top = padding, start = padding, end = padding)
                            .fillMaxWidth()
                    )
                    CategoryChooserComponent(
                        transactionState,
                        setCategoriesIds,
                        onEmptyCategoryClick
                    )
                    if (transactionState.categoryState is CategorySuccess && transactionState.categoryState.listCategory.isEmpty()) {
                        ShortSuccessCategoryComponent(
                            stringResource(R.string.no_categories_income),
                            onEmptyCategoryClick
                        )
                    }
                }
                is TransactionExpenseState -> {
                    Text(
                        stringResource(R.string.title_categories_expense),
                        style = UiKitTypography.TextMD.Regular,
                        modifier = Modifier
                            .padding(top = padding, start = padding, end = padding)
                            .fillMaxWidth()
                    )
                    CategoryChooserComponent(
                        transactionState,
                        setCategoriesIds,
                        onEmptyCategoryClick
                    )
                    if (transactionState.categoryState is CategorySuccess && transactionState.categoryState.listCategory.isEmpty()) {
                        ShortSuccessCategoryComponent(
                            stringResource(R.string.no_categories_expense),
                            onEmptyCategoryClick
                        )
                    }
                }
                is TransactionTransferState -> {}
            }
        }
    }

}

@Composable
private fun TransactionButtonComponent(
    isLoading: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = UiKitColors.colors.neutral.`1100`,
                modifier = Modifier.padding(bottom = 30.dp)
            )
        } else {
            UiKitButton(
                info = UiKitButtonInfo(
                    text = stringResource(R.string.btn_text_create),
                    size = ButtonSize.MEDIUM
                ),
                onClick = onClick,
                modifier = Modifier.padding(bottom = 30.dp),
                isButtonEnabled = enabled
            )
        }
    }


}

fun transactionTopBar(viewModel: ITransactionsViewModel) = @Composable {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Icon(
            imageVector = ArrowLeft,
            contentDescription = null,
            modifier = Modifier.clickable(onClick = viewModel::back)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = stringResource(R.string.create_screen_title),
            style = UiKitTypography.DisplayXS.Regular
        )
        Spacer(modifier = Modifier.width(15.dp))
    }
}