package ru.bysoft.android.budget.uikit.components.currencyfield

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.PopupFieldState
import ru.bysoft.android.budget.uikit.styles.UiKitTypography

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
        if (!info.errorText.isNullOrEmpty()) {
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
                DropdownMenuItem(
                    onClick = {
                        showMenu.value = false
                        onClickItem(item)
                    }
                ) {
                    itemInPopup(item)
                }
            }
        }
    }
}