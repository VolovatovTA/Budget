package ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.filters

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.filters.FilterData
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.filters.FilterState
import ru.bysoft.budget.uikit.components.buttons.UiKitToggleButton
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo

@Composable
fun HomeFiltersComponent(filtersState: FilterState, onCheckChanged: (Boolean, FilterData) -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.width(30.dp))
        filtersState.listFilters.forEach { filterData ->
            UiKitToggleButton(
                UiKitButtonInfo(
                    text = stringResource(filterData.type.text), type = ButtonType.SMALL
                ),
                checked = filterData.isChecked,
                enabled = filterData.isEnabled,
                onCheckedChange = { onCheckChanged(it, filterData) }
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
        Spacer(modifier = Modifier.width(15.dp))
    }
}