package ru.bysoft.android.budget.features.create_udate_category.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.features.create_udate_category.presentation.entity.CreateUpdateCategoryState
import ru.bysoft.android.budget.features.create_udate_category.presentation.entity.IconState
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.avatar.UiKitAvatar
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState
import ru.bysoft.android.budget.uikit.icons.UiKitIcons
import ru.bysoft.android.budget.uikit.styles.UiKitStyles
import ru.bysoft.android.budget.uikit.R

@Composable
fun IconsComponent(onClick: (String?) -> Unit, selectedIcon: IconState) {
    val listIcons = UiKitIcons.getCategories().map { it.second }.plus(null)
    LazyVerticalGrid(
        columns = GridCells.Adaptive(45.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
    ) {
        items(listIcons.size) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val icon = listIcons[it]
                if (icon != null) {
                    UiKitAvatar(
                        icon = icon,
                        backgroundColor =
                        if (icon.name == selectedIcon.iconName) Color(0x22000000) else
                            Color.Transparent,
                        onClick = { onClick(icon.name) },
                        rippleEnabled = false,
                        elevation = 0.dp
                    )
                } else {
                    Text(
                        text = stringResource(R.string.without_icon),
                        style = UiKitStyles.Caption,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable(
                                remember { MutableInteractionSource() },
                                indication = null
                            ) { onClick(null) }
                            .background(
                                if (selectedIcon.iconName == null) Color(0x22000000)
                                else Color.Transparent
                            )
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }

            }

        }
    }
}

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
                info = UiKitButtonInfo(text, type = ButtonType.MEDIUM),
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
            colors = UiKitColors.colors.textFieldColors,
            label = {
                Text(
                    text = label,
                    style = UiKitStyles.Body2
                )
            },
            isError = state.errorText != null,
            interactionSource = source,
            textStyle = UiKitStyles.Body2,
            keyboardActions = keyboardActions
        )
        if (state.errorText != null && state.errorText != R.string.empty_text) {
            Text(
                text = stringResource(state.errorText!!),
                style = UiKitStyles.Caption,
                color = UiKitColors.colors.red
            )
        }
    }
}