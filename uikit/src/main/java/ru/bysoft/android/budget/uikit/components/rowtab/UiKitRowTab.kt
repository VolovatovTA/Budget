package ru.bysoft.android.budget.uikit.components.rowtab

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.components.buttons.UiKitToggleButton
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitRowTabState

@Composable
fun UiKitRowTab(
    startState: UiKitRowTabState,
    onCheckChanged: (Boolean, Int) -> Unit,
    isMultiplyCheckedEnabled: Boolean = false
) {
    var innerState by remember { mutableStateOf(startState) }

    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.width(30.dp))
        innerState.listFilters.forEachIndexed { index, filterData ->
            UiKitToggleButton(
                filterData.info,
                checked = filterData.isChecked,
                enabled = filterData.isEnabled,
                onCheckedChange = {
                    innerState = UiKitRowTabState(
                        innerState.listFilters.mapIndexed { indexTab, tab ->
                            when {
                                indexTab == index -> tab.copy(isChecked = true)
                                isMultiplyCheckedEnabled -> tab
                                else -> tab.copy(isChecked = false)
                            }
                        }
                    )
                    onCheckChanged(it, index)
                }
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
        Spacer(modifier = Modifier.width(15.dp))
    }
}