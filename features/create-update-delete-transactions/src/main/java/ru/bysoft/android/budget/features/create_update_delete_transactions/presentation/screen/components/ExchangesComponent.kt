package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.common.util.BudgetCurrency
import ru.bysoft.android.budget.features.create_update_delete_transactions.R
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.ExchangeFieldState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.HEIGHT_ELEMENT
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.MAX_HEIGHT
import ru.bysoft.android.budget.uikit.components.textfield.UiKitTextField
import ru.bysoft.android.budget.uikit.styles.UiKitStyles

@Composable
fun ExchangesComponent(
    exchangeFieldState: List<ExchangeFieldState> = listOf(
        ExchangeFieldState(),
        ExchangeFieldState(),
    ),
    setAmount: (BudgetCurrency, String) -> Unit = { _, _ -> },
    mainCurrency: BudgetCurrency?,
    focusManager: FocusManager,
) {
    Column(
        modifier = Modifier
            .heightIn(max = MAX_HEIGHT)
            .verticalScroll(rememberScrollState())
            .animateContentSize()
    ) {
        if (exchangeFieldState.isNotEmpty()) {
            Text(
                text = stringResource(R.string.text_field_exchange_not_empty),
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
                style = UiKitStyles.Body2,
            )
        }
        exchangeFieldState.forEachIndexed { index, exchangeState ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                val text = exchangeState
                    .currencyFieldState
                    .selectedCurrency
                    ?.displayName ?: "???"
                Text(
                    text = "1 $text = ",
                    modifier = Modifier
                        .padding(start = 30.dp, top = 9.dp)
                )
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
                    label = stringResource(R.string.text_field_exchange_label),
                    inputType = KeyboardType.Number,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .weight(2f)
                        .height(HEIGHT_ELEMENT),
                    keyboardActions = KeyboardActions {
                        if (index == exchangeFieldState.lastIndex){
                            focusManager.clearFocus()
                        } else {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    },
                )
                mainCurrency?.displayName?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(end = 30.dp, top = 9.dp)
                    )
                }
            }
        }
    }
}