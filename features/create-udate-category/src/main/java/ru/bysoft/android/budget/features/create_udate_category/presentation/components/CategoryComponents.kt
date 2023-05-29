package ru.bysoft.android.budget.features.create_udate_category.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.features.create_udate_category.presentation.entity.CreateUpdateCategoryState
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState
import ru.bysoft.android.budget.uikit.styles.UiKitTypography

@Composable
fun ButtonComponent(
    onClickCreateUpdate: () -> Unit,
    state: CreateUpdateCategoryState,
    text: String
) {
    Box(modifier = Modifier.height(50.dp)) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxHeight())
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
) {
    val source = remember { MutableInteractionSource() }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = state.text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { if (!it.isFocused) onNotFocused(state.text) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = type
            ),
            shape = RoundedCornerShape(10.dp),
            colors = UiKitColors.textField,
            label = {
                Text(
                    text = label,
                    style = UiKitTypography.TextMD.Regular
                )
            },
            isError = state.errorText != null,
            interactionSource = source,
            textStyle = UiKitTypography.TextMD.Regular,
            keyboardActions = keyboardActions
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