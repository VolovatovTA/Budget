package ru.bysoft.budget.create_update_delete_transactions.presentation.screen

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.common.util.BudgetCurrency
import ru.bysoft.budget.create_update_delete_transactions.presentation.entity.*
import ru.bysoft.budget.create_update_delete_transactions.presentation.screen.components.CategoryChooserComponent
import ru.bysoft.budget.create_update_delete_transactions.presentation.screen.components.ExchangesComponent
import ru.bysoft.budget.create_update_delete_transactions.presentation.screen.components.WalletChooserComponent
import ru.bysoft.budget.create_update_delete_transactions.viewmodels.ITransactionsViewModel
import ru.bysoft.budget.create_update_delete_transactions.viewmodels.TransactionCreateViewModel
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.budget.uikit.components.currecyfield.UiKitCurrencyPopUp
import ru.bysoft.budget.uikit.components.rowtab.UiKitRowTab
import ru.bysoft.budget.uikit.components.rowtab.entity.UiKitTabInfo
import ru.bysoft.budget.uikit.components.rowtab.entity.UiKitRowTabState
import ru.bysoft.budget.uikit.components.textfield.UiKitTextField
import ru.bysoft.budget.uikit.icons.pack.ArrowLeft
import ru.bysoft.budget.uikit.styles.UiKitStyles

internal val HEIGHT_ELEMENT = 60.dp
internal val MAX_HEIGHT = 200.dp

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
    Scaffold(topBar = transactionTopBar(viewModel)) { paddingValues ->
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
                )
                TransactionButtonComponent(
                    transactionState.isLoading,
                    viewModel::create,
                    Modifier.weight(1f)
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
    transactionState: ITransactionState = TransactionTransferState(),
    setCategoriesIds: (CategoryPresentation) -> Unit = {},
    setWalletId: (fromId: String?, toId: String?) -> Unit = { _, _ -> },
    setComment: (String) -> Unit = {},
    setAmount: (String) -> Unit = {},
    setCurrency: (BudgetCurrency) -> Unit = {},
    onButtonClick: () -> Unit = {},
    setExchangeAmount: (BudgetCurrency, String) -> Unit = { _, _ -> },
    setTransactionType: (TransactionTypeEnum) -> Unit = { },
) {
    Column(
        modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


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
                            it.text,
                            type = ButtonType.SMALL
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
        Text(
            "Кошельки",
            style = UiKitStyles.Body2,
            modifier = Modifier
                .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth()
        )

        Row(Modifier.padding(horizontal = 25.dp)) {
            transactionState.walletFromFieldState?.let { iWalletFieldState ->
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Кошелёк с которого хотите снять деньги",
                        style = UiKitStyles.Body2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    )
                    WalletChooserComponent(
                        iWalletFieldState
                    ) { fromId -> setWalletId(fromId, null) }
                }

            }
            transactionState.walletToFieldState?.let { iWalletFieldState ->
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Кошелёк который хотите пополнить",
                        style = UiKitStyles.Body2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
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
                label = "Сумма",
                inputType = KeyboardType.Number,
                modifier = Modifier
                    .padding(start = 30.dp, top = 5.dp, end = 5.dp)
                    .weight(1f)
                    .height(HEIGHT_ELEMENT)
            )
            UiKitCurrencyPopUp(
                info = transactionState.currencyFieldState,
                onNameChanged = setCurrency,
                modifier = Modifier
                    .padding(end = 30.dp, top = 18.dp, bottom = 5.dp, start = 5.dp)
                    .weight(1f)
                    .height(HEIGHT_ELEMENT - 8.dp)
            )
        }
        UiKitTextField(
            state = transactionState.commentState,
            onValueChange = setComment,
            label = "Коментарий",
            inputType = KeyboardType.Text,
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 5.dp)
                .height(HEIGHT_ELEMENT)
        )

        ExchangesComponent(
            transactionState.exchangeFieldState,
            setAmount = setExchangeAmount
        )

        when (transactionState) {
            is TransactionIncomeState -> {
                Text(
                    "Категории дохода",
                    style = UiKitStyles.Body2,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 40.dp, end = 30.dp)
                        .fillMaxWidth()
                )
                CategoryChooserComponent(transactionState, setCategoriesIds)
            }
            is TransactionExpenseState -> {
                Text(
                    "Категории расхода",
                    style = UiKitStyles.Body2,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 40.dp, end = 30.dp)
                        .fillMaxWidth()
                )
                CategoryChooserComponent(transactionState, setCategoriesIds)
            }
            is TransactionTransferState -> {}
        }


    }

}

@Composable
private fun TransactionButtonComponent(
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    typeEnum: TransactionTypeEnum = TransactionTypeEnum.EXPENSE
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = UiKitColors.colors.grey,
                modifier = Modifier.padding(bottom = 30.dp)
            )
        } else {
            UiKitButton(
                info = UiKitButtonInfo(
                    text = "Создать транзакцию",
                    type = ButtonType.MEDIUM
                ),
                onClick = onClick,
                modifier = Modifier.padding(bottom = 30.dp)
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
            text = "Создание транзакции", style = UiKitStyles.H2
        )
        Spacer(modifier = Modifier.width(15.dp))
    }
}