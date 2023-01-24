package ru.bysoft.budget.create_update_delete_transactions.presentation.screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.common.util.BudgetCurrency
import ru.bysoft.budget.create_update_delete_transactions.presentation.entity.ExchangeFieldState
import ru.bysoft.budget.create_update_delete_transactions.presentation.screen.HEIGHT_ELEMENT
import ru.bysoft.budget.create_update_delete_transactions.presentation.screen.MAX_HEIGHT
import ru.bysoft.budget.uikit.components.currecyfield.UiKitCurrencyPopUp
import ru.bysoft.budget.uikit.components.textfield.UiKitTextField

@Composable
fun ExchangesComponent(
    exchangeFieldState: List<ExchangeFieldState> = listOf(
        ExchangeFieldState(),
        ExchangeFieldState(),
    ),
    setAmount: (BudgetCurrency, String) -> Unit,
    setCurrency: (pos: Int, cur: BudgetCurrency) -> Unit
) {
    Column(
        modifier = Modifier
            .heightIn(max = MAX_HEIGHT)
            .verticalScroll(rememberScrollState())
    ) {
        exchangeFieldState.forEachIndexed { index, exchangeState ->
            Row {
                UiKitTextField(
                    state = exchangeState.amount,
                    onValueChange = { amount ->
                        exchangeState.currencyFieldState.selectedCurrency?.let { currency ->
                            setAmount(
                                currency,
                                amount
                            )
                        }
                    },
                    label = "Курс",
                    inputType = KeyboardType.Number,
                    modifier = Modifier
                        .padding(start = 30.dp, top = 5.dp, end = 5.dp)
                        .weight(1f)
                        .height(HEIGHT_ELEMENT)
                )
                UiKitCurrencyPopUp(
                    info = exchangeState.currencyFieldState,
                    onNameChanged = { cur ->
                        setCurrency(
                            index,
                            cur
                        )
                    },
                    modifier = Modifier
                        .padding(end = 30.dp, top = 13.dp, bottom = 5.dp, start = 5.dp)
                        .weight(1f)
                        .height(HEIGHT_ELEMENT - 8.dp)
                )
            }
        }
    }

}