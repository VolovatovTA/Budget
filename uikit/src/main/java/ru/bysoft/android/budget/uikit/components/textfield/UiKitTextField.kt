package ru.bysoft.android.budget.uikit.components.textfield

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.currency.currencyWithFlags
import ru.bysoft.android.budget.currency.getAvailableCurrency
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.currencyfield.UiKitPopUp
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.PopupFieldState
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.halfCorner
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding
import ru.bysoft.android.budget.uikit.theme.NoRippleTheme


data class TextFieldState(
    val text: String = "",
    val errorText: Int? = null,
)

@Composable
fun <T> UiKitTextFieldWithPopUpAndCurrency(
    state: TextFieldState,
    onValueChange: (String) -> Unit,
    label: String,
    inputType: KeyboardType,
    modifier: Modifier = Modifier,
    onNotFocused: (lastText: String) -> Unit = {},
    keyboardActions: KeyboardActions,
    popUpList: PopupFieldState<T>? = null,
    popupItem: (@Composable (T?) -> Unit)? = null,
    onSelectPopupItem: (T) -> Unit = {},
    currencyPopUpList: CurrencyFieldState? = null,
    currencyItem: (@Composable (BudgetCurrencyEnum?) -> Unit)? = null,
    onSelectCurrency: (BudgetCurrencyEnum) -> Unit = {},
) {
    val source = remember { MutableInteractionSource() }

    var expandedCurrency by remember { mutableStateOf(false) }
    var expandedPopUp by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = state.text,
            onValueChange = onValueChange,
            modifier = modifier
                .onFocusChanged { if (!it.isFocused) onNotFocused(state.text) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = inputType
            ),
            shape = RoundedCornerShape(10.dp),
            colors = UiKitColors.textField,
            label = {
                Text(
                    label,
                    style = UiKitTypography.TextSM.Medium,
                    color = UiKitColors.colors.type.high,
                )
            },
            isError = state.errorText != null,
            interactionSource = source,
            keyboardActions = keyboardActions,
            trailingIcon = {
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        currencyPopUpList?.let {
                            Box(
                                Modifier.clickable { expandedCurrency = true }
                            ) {
                                currencyPopUpList.selectedCurrency?.let {
                                    currencyItem?.invoke(it)
                                }
                            }
                            DropdownMenu(
                                expanded = expandedCurrency,
                                onDismissRequest = { expandedCurrency = false },
                            ) {
                                currencyPopUpList.list.forEach {
                                    DropdownMenuItem(
                                        onClick = {
                                            expandedCurrency = false
                                            onSelectCurrency(it)
                                        },
                                    ) {
                                        currencyItem?.invoke(it)
                                    }
                                }
                            }
                        }
                        popUpList?.let {
                            Box(
                                Modifier.clickable { expandedPopUp = true }
                            ) {
                                popupItem?.invoke(popUpList.selectedValue)
                            }
                            DropdownMenu(
                                expanded = expandedPopUp,
                                onDismissRequest = { expandedPopUp = false },
                            ) {
                                popUpList.list.forEach {
                                    DropdownMenuItem(
                                        onClick = {
                                            expandedPopUp = false
                                            onSelectPopupItem(it)
                                        },
                                    ) {
                                        popupItem?.invoke(it)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            visualTransformation = if (inputType == KeyboardType.Password) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
        )
        if (state.errorText != null && state.errorText != R.string.empty_text) {
            Text(
                text = stringResource(id = state.errorText),
                style = UiKitTypography.TextSM.Medium,
                color = UiKitColors.colors.feedbackRed.`600`,
                modifier = Modifier
                    .offset(y = 61.dp)
                    .padding(horizontal = padding)
            )
        }
    }
}


@Composable
fun UiKitTextFieldWithCurrency(
    state: TextFieldState,
    onValueChange: (String) -> Unit,
    label: String,
    inputType: KeyboardType,
    modifier: Modifier = Modifier,
    onNotFocused: (lastText: String) -> Unit = {},
    keyboardActions: KeyboardActions,
    popUpList: CurrencyFieldState? = null,
    popUpItem: (@Composable (BudgetCurrencyEnum?) -> Unit)? = null,
    onSelectPopUpItem: (BudgetCurrencyEnum) -> Unit = {},
) {
    val source = remember { MutableInteractionSource() }

    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = state.text,
            onValueChange = onValueChange,
            modifier = modifier
                .onFocusChanged { if (!it.isFocused) onNotFocused(state.text) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = inputType
            ),
            shape = RoundedCornerShape(10.dp),
            colors = UiKitColors.textField,
            label = {
                Text(
                    label,
                    style = UiKitTypography.TextSM.Medium,
                    color = UiKitColors.colors.type.high,
                )
            },
            isError = state.errorText != null,
            interactionSource = source,
            keyboardActions = keyboardActions,
            trailingIcon = {
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    popUpList?.let {
                        Box(
                            Modifier.clickable { expanded = true }
                        ) {
                            popUpList.selectedCurrency?.let {
                                popUpItem?.invoke(it)
                            }
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(UiKitColors.colors.surface.primary)
                        ) {
                            popUpList.list.forEach {
                                DropdownMenuItem(
                                    onClick = {
                                        expanded = false
                                        onSelectPopUpItem(it)
                                    },
                                    modifier = Modifier.background(UiKitColors.colors.surface.primary)
                                ) {
                                    popUpItem?.invoke(it)
                                }
                            }
                        }
                    }
                }
            },
            visualTransformation = if (inputType == KeyboardType.Password) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
        )
        if (state.errorText != null && state.errorText != R.string.empty_text) {
            Text(
                text = stringResource(id = state.errorText),
                style = UiKitTypography.TextSM.Medium,
                color = UiKitColors.colors.feedbackRed.`600`,
                modifier = Modifier
                    .offset(y = 61.dp)
                    .padding(horizontal = padding)
            )
        }
    }
}

@Composable
fun UiKitTextField(
    state: TextFieldState,
    onValueChange: (String) -> Unit,
    label: String,
    inputType: KeyboardType,
    modifier: Modifier = Modifier,
    onNotFocused: (lastText: String) -> Unit = {},
    keyboardActions: KeyboardActions,
) {
    val source = remember { MutableInteractionSource() }

    OutlinedTextField(
        value = state.text,
        onValueChange = onValueChange,
        modifier = modifier
            .onFocusChanged { if (!it.isFocused) onNotFocused(state.text) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = inputType
        ),
        shape = RoundedCornerShape(10.dp),
        colors = UiKitColors.textField,
        label = {
            Text(
                label,
                style = UiKitTypography.TextSM.Medium,
                color = UiKitColors.colors.type.high,
            )
        },
        isError = state.errorText != null,
        interactionSource = source,
        keyboardActions = keyboardActions,
        visualTransformation = if (inputType == KeyboardType.Password) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
    )
    if (state.errorText != null && state.errorText != R.string.empty_text) {
        Text(
            text = stringResource(id = state.errorText),
            style = UiKitTypography.TextSM.Medium,
            color = UiKitColors.colors.feedbackRed.`600`,
            modifier = Modifier
                .offset(y = 61.dp)
                .padding(horizontal = padding)
        )
    }
}

val listTextFieldState = listOf(
    TextFieldState() to "",
    TextFieldState() to "Label",
    TextFieldState("Main text") to "",
    TextFieldState("Main text") to "Label",
)

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
//    widthDp = 1000
)
@Composable
fun TextFieldPreviewLight() {
    Row(horizontalArrangement = Arrangement.spacedBy(padding + halfPadding)) {
//        Column(verticalArrangement = Arrangement.spacedBy(padding + halfPadding)) {
//            listTextFieldState.forEach {
//                UiKitTextField(
//                    state = it.first,
//                    onValueChange = {},
//                    inputType = KeyboardType.Text,
//                    label = it.second,
//                    keyboardActions = KeyboardActions { }
//                )
//            }
//        }
//        Column(verticalArrangement = Arrangement.spacedBy(padding + halfPadding)) {
//            listTextFieldState.forEach {
//                UiKitTextField(
//                    state = it.first.copy(errorText = R.string.currency_not_selected),
//                    onValueChange = {},
//                    inputType = KeyboardType.Password,
//                    label = it.second,
//                    keyboardActions = KeyboardActions { }
//                )
//            }
//        }
        Column(verticalArrangement = Arrangement.spacedBy(padding + halfPadding)) {
            listTextFieldState.forEachIndexed { i, pair ->
                UiKitTextFieldWithCurrency(
                    state = pair.first.copy(errorText = R.string.currency_not_selected),
                    onValueChange = {},
                    inputType = KeyboardType.Text,
                    label = pair.second,
                    keyboardActions = KeyboardActions { },
                    popUpList = CurrencyFieldState(
                        selectedCurrency = getAvailableCurrency()[1]
                    ),
                    popUpItem = { t ->
                        t?.let {
                            UiKitCurrencyPopUpTextField(t)
                        }
                    }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF0E1216,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 1000
)
@Composable
fun TextFieldPreviewDark() {
    TextFieldPreviewLight()
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
//    widthDp = 1000
)
@Composable
fun TextFieldPreview() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        UiKitTextField(
            state = listTextFieldState[0].first,
            onValueChange = {},
            label = "",
            inputType = KeyboardType.Text,
            keyboardActions = KeyboardActions { }
        )
        Spacer(modifier = Modifier.width(500.dp))

    }
}

@Preview
@Composable
fun PopupPreview() {
    UiKitPopUp(
        info = PopupFieldState(
            list = currencyWithFlags,
            selectedValue = currencyWithFlags[1]
        ), onClickItem = {}) { t ->
        t?.let {
            UiKitCurrencyPopUpTextField(t.first)
        }
    }
}

@Composable
fun UiKitCurrencyPopUpTextField(currency: BudgetCurrencyEnum) {
    Row(
        modifier = Modifier
            .padding(halfPadding)
            .clip(RoundedCornerShape(halfCorner)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = currency.displayName,
            style = UiKitTypography.TextSM.Medium,
            color = UiKitColors.colors.type.high,
            modifier = Modifier
                .padding(end = halfPadding)
        )
        val icon = currencyWithFlags.firstOrNull { it.first == currency }?.second
        icon?.let {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = halfPadding)
            )
        }
    }
}