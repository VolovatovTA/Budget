package ru.bysoft.budget.uikit.components.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.styles.UiKitStyles


data class TextFieldState(
    val text: String = "",
    val errorText: Int? = null,
)

@Composable
fun UiKitTextField(
    state: TextFieldState,
    onValueChange: (String) -> Unit,
    label: String,
    inputType: KeyboardType,
    modifier: Modifier = Modifier,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = state.text,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = inputType
        ),
        shape = RoundedCornerShape(10.dp),
        colors = UiKitColors.colors.textFieldColors,
        label = { Text(label, maxLines = maxLines,) },
        isError = state.errorText != null,
        textStyle = UiKitStyles.Body2,
//        singleLine = true
    )
}