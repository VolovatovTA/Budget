package ru.bysoft.android.budget.features.create_udate_category.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.features.create_udate_category.presentation.entity.CreateUpdateCategoryState
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState
import ru.bysoft.android.budget.uikit.components.textfield.UiKitCurrencyPopUpTextField
import ru.bysoft.android.budget.uikit.components.textfield.UiKitTextFieldWithCurrency
import ru.bysoft.android.budget.uikit.styles.UiKitTypography

@Composable
fun ButtonComponent(
    onClickCreateUpdate: () -> Unit,
    state: CreateUpdateCategoryState,
    text: String
) {
    Box(modifier = Modifier.height(50.dp)) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        } else {
            UiKitButton(
                info = UiKitButtonInfo(text, size = ButtonSize.MEDIUM),
                onClick = onClickCreateUpdate
            )
        }
    }

}

@Composable
fun CreateUpdateCategoryTextField(
    state: TextFieldState,
    onTextChange: (String) -> Unit,
    label: String,
    type: KeyboardType,
    modifier: Modifier = Modifier,
    onNotFocused: (lastText: String) -> Unit = {},
    keyboardActions: KeyboardActions,
    popUpList: CurrencyFieldState<BudgetCurrencyEnum>? = null,
    onCurrencySelected: ((BudgetCurrencyEnum) -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {

        UiKitTextFieldWithCurrency(
            state = state,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { if (!it.isFocused) onNotFocused(state.text) },
            label = label,
            keyboardActions = keyboardActions,
            inputType = type,
            popUpList = popUpList,
            popUpItem = {
                it?.let {
                    UiKitCurrencyPopUpTextField(it.displayName, it.flag)
                }
            },
            onSelectPopUpItem = {
                onCurrencySelected?.invoke(it)
            }
        )
        if (state.errorText != null && state.errorText != R.string.empty_text) {
            Text(
                text = stringResource(state.errorText!!),
                style = UiKitTypography.TextXS.Regular,
                color = UiKitColors.colors.feedbackRed.`1100`
            )
        }
    }
}