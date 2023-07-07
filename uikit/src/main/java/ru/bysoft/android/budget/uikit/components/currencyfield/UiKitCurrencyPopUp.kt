package ru.bysoft.android.budget.uikit.components.currencyfield

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.PopupFieldState
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import java.util.*

@Composable
fun UiKitCurrencyPopUp(
    info: CurrencyFieldState,
    onNameChanged: (BudgetCurrencyEnum) -> Unit,
    modifier: Modifier = Modifier
) {
    val showMenu = remember { mutableStateOf(false) }
    Spacer(modifier = Modifier.height(20.dp))

    Column(
        modifier = modifier
            .heightIn(min = 50.dp, max = 100.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(0.8.dp, UiKitColors.colors.type.high),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            color = Color.Transparent
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable { showMenu.value = !showMenu.value }
                .background(Color.Transparent)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(15.dp))
                val text =
                    if (info.selectedCurrency != null) info.selectedCurrency.displayName
                    else stringResource(R.string.currency_not_selected)

                Text(
                    text = text,
                    style = UiKitTypography.TextMD.Regular,
                    color = UiKitColors.colors.type.high,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = if (showMenu.value) {
                            Icons.Filled.ArrowDropUp
                        } else {
                            Icons.Filled.ArrowDropDown
                        },
                        contentDescription = null,
                        tint = UiKitColors.colors.type.high,
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
            }
        }
        if (info.errorText != null && info.errorText != R.string.empty_text) {
            Text(
                text = stringResource(info.errorText),
                style = UiKitTypography.TextXS.Regular,
                color = UiKitColors.colors.feedbackRed.`1100`,
            )
        }
        DropdownMenu(
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value = false },
            modifier = Modifier
        ) {

            info.list.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onNameChanged(item)
                        showMenu.value = false
                    },
                    modifier = Modifier
                        .background(UiKitColors.colors.surface.primary)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(UiKitColors.colors.surface.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.displayName,
                            style = UiKitTypography.DisplayXS.Regular,
                            color = UiKitColors.colors.type.high,
                            modifier = Modifier
                                .fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = Currency.getInstance(item.iso4217).displayName,
                        style = UiKitTypography.TextMD.Regular,
                        color = UiKitColors.colors.type.high,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun <T> UiKitPopUp(
    info: PopupFieldState<T>,
    onClickItem: (T) -> Unit,
    modifier: Modifier = Modifier,
    itemInPopup: @Composable (T?) -> Unit,
) {
    val showMenu = remember { mutableStateOf(false) }
    Spacer(modifier = Modifier.height(20.dp))

    Column(
        modifier = modifier.clickable { showMenu.value = !showMenu.value }
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(
                0.8.dp,
                if (info.errorText == null || info.errorText.isEmpty()) UiKitColors.colors.type.high
                else UiKitColors.colors.feedbackRed.`500`
            ),
            modifier = modifier,
            color = Color.Transparent
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                itemInPopup(info.selectedValue)
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = if (showMenu.value) {
                            Icons.Filled.ArrowDropUp
                        } else {
                            Icons.Filled.ArrowDropDown
                        },
                        contentDescription = null,
                        tint = UiKitColors.colors.type.high,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        if (info.errorText != null && info.errorText.isNotEmpty()) {
            Text(
                text = info.errorText,
                style = UiKitTypography.TextXS.Regular,
                color = UiKitColors.colors.feedbackRed.`1100`,
            )
        }
        DropdownMenu(
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value = false },
            modifier = Modifier
        ) {

            info.list.forEach { item ->
                DropdownMenuItem(onClick = {
                    showMenu.value = false
                    onClickItem(item)
                }) {
                    itemInPopup(item)
                }
            }
        }
    }
}