package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.currency.getCurrency
import ru.bysoft.android.budget.features.create_update_delete_transactions.R
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.ExchangeFieldState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.HEIGHT_ELEMENT
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.padding
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.textfield.UiKitTextField
import ru.bysoft.android.budget.uikit.icons.pack.Recycle
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.quarterPadding

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
@Composable
fun ExchangesComponent(
    modifier: Modifier = Modifier,
    exchangeFieldState: List<ExchangeFieldState> = listOf(
        ExchangeFieldState(baseCurrency = getCurrency("USD"), targetCurrency = getCurrency("RUB")),
        ExchangeFieldState(baseCurrency = getCurrency("USD"), targetCurrency = getCurrency("RUB")),
        ExchangeFieldState(
            baseCurrency = getCurrency("USD"),
            targetCurrency = getCurrency("RUB"),
            isFullAmount = true
        ),
        ExchangeFieldState(
            baseCurrency = getCurrency("USD"),
            targetCurrency = getCurrency("RUB"),
            isRevert = true
        ),
    ),
    setAmount: (BudgetCurrencyEnum, String) -> Unit = { _, _ -> },
    focusManager: FocusManager = LocalFocusManager.current,
    setFullAmount: (BudgetCurrencyEnum, Boolean) -> Unit = { _, _ -> },
    setRevert: (BudgetCurrencyEnum, Boolean) -> Unit = { _, _ -> },
) {
    Column(
        modifier = Modifier
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(padding)
    ) {
        if (exchangeFieldState.isNotEmpty()) {
            Text(
                text = stringResource(R.string.text_field_exchange_not_empty),
                modifier = Modifier.padding(horizontal = padding, vertical = padding / 2),
                style = UiKitTypography.TextMD.Regular,
            )
        }
        exchangeFieldState.forEachIndexed { index, exchangeState ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(padding),
                modifier = Modifier
                    .height(HEIGHT_ELEMENT)
                    .padding(horizontal = padding)
                    .fillMaxWidth()
            ) {

                val baseCurrencyName = exchangeState
                    .baseCurrency
                    .displayName

                val shownAmount = exchangeState.shownAmount.toString()
                if (exchangeState.isRevert) {
                    Box(modifier = Modifier.weight(1f)) {
                        UiKitTextField(
                            state = exchangeState.enteredAmount,
                            onValueChange = { amount ->
                                setAmount(
                                    exchangeState.targetCurrency,
                                    amount
                                )
                            },
                            label = stringResource(R.string.text_field_exchange_label),
                            inputType = KeyboardType.Number,
                            modifier = modifier,
                            keyboardActions = KeyboardActions {
                                if (index == exchangeFieldState.lastIndex) {
                                    focusManager.clearFocus()
                                } else {
                                    focusManager.moveFocus(FocusDirection.Next)
                                }
                            },
                        )
                    }
                    Text(
                        text = "$baseCurrencyName = $shownAmount ${exchangeState.targetCurrency.displayName}",
                        style = UiKitTypography.TextMD.Regular,
                        modifier = Modifier
                            .padding(top = halfPadding)
                    )
                } else {
                    Text(
                        text = "$shownAmount $baseCurrencyName = ",
                        style = UiKitTypography.TextMD.Regular,
                        modifier = Modifier
                            .padding(top = halfPadding)
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        UiKitTextField(
                            state = exchangeState.enteredAmount,
                            onValueChange = { amount ->
                                setAmount(
                                    exchangeState.targetCurrency,
                                    amount
                                )
                            },
                            label = stringResource(R.string.text_field_exchange_label),
                            inputType = KeyboardType.Number,
                            keyboardActions = KeyboardActions {
                                if (index == exchangeFieldState.lastIndex) {
                                    focusManager.clearFocus()
                                } else {
                                    focusManager.moveFocus(FocusDirection.Next)
                                }
                            },
                        )
                    }

                    Text(
                        text = exchangeState.targetCurrency.displayName,
                        textAlign = TextAlign.Center,
                        style = UiKitTypography.TextMD.Regular,
                        modifier = Modifier
                            .padding(top = halfPadding)
                    )
                }

                val backgroundIcon = UiKitColors.card(isSelected = exchangeState.isRevert)

                val backgroundText = UiKitColors.card(isSelected = exchangeState.isFullAmount)

                val height = 0.8f * HEIGHT_ELEMENT
                val shape = RoundedCornerShape(8.dp)

                if (!exchangeState.isRevert) {
                    Surface(
                        shape = shape,
                        modifier = Modifier
                            .padding(top = halfPadding),
                        color = backgroundText,
                        elevation = 3.dp,
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(quarterPadding)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        setFullAmount(
                                            exchangeState.targetCurrency,
                                            exchangeState.isFullAmount.not()
                                        )
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.full_amount),
                                textAlign = TextAlign.Center,
                                style = UiKitTypography.TextXS.Regular,
                                color = UiKitColors.colors.type.high
                            )
                        }
                    }
                }


                if (!exchangeState.isFullAmount) {
                    Surface(
                        shape = shape,
                        modifier = Modifier
                            .size(height)
                            .padding(top = halfPadding),
                        color = backgroundIcon,
                        elevation = 3.dp,
                    ) {
                        Icon(
                            imageVector = Recycle,
                            contentDescription = null,
                            tint = UiKitColors.colors.type.high,
                            modifier = Modifier
                                .padding(halfPadding)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        setRevert(
                                            exchangeState.targetCurrency,
                                            exchangeState.isRevert.not()
                                        )
                                    }
                                )
                        )
                    }
                }
            }
        }
    }
}