package ru.bysoft.budget.create_update_delete_transactions.presentation.screen.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.common.util.BudgetCurrency
import ru.bysoft.budget.create_update_delete_transactions.presentation.entity.ExchangeFieldState
import ru.bysoft.budget.create_update_delete_transactions.presentation.screen.HEIGHT_ELEMENT
import ru.bysoft.budget.create_update_delete_transactions.presentation.screen.MAX_HEIGHT
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.textfield.UiKitTextField
import java.util.Currency

@Preview
@Composable
fun ExchangesComponent(
    exchangeFieldState: List<ExchangeFieldState> = listOf(
        ExchangeFieldState(),
        ExchangeFieldState(),
    ),
    setAmount: (BudgetCurrency, String) -> Unit = { _, _ -> },
) {
    Column(
        modifier = Modifier
            .heightIn(max = MAX_HEIGHT)
            .verticalScroll(rememberScrollState())
    ) {
        exchangeFieldState.forEach { exchangeState ->
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
                val text = Currency
                    .getInstance(
                        exchangeState
                            .currencyFieldState
                            .selectedCurrency
                            ?.iso4217 ?: "TRY"
                    ).displayName
                Surface(
                    modifier = Modifier
                        .padding(end = 30.dp, top = 13.dp, bottom = 5.dp, start = 5.dp)
                        .weight(1f)
                        .height(HEIGHT_ELEMENT - 8.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, UiKitColors.colors.dark),
                    color = Color.Transparent
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = text,
                            modifier = Modifier
                                .padding(start = 20.dp)
                        )
                    }
                }
            }
        }
    }

}