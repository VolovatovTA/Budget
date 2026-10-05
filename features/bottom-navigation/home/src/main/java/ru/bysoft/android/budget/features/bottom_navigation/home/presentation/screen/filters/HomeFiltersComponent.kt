package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.filters

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.bysoft.android.budget.uikit.components.rowtab.UiKitRowTab
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitRowTabState
import ru.bysoft.android.budget.uikit.styles.padding

@Composable
fun HomeFiltersComponent(filtersState: UiKitRowTabState, onCheckChanged: (Boolean, Int) -> Unit) {

    UiKitRowTab(
        startState = filtersState,
        onCheckChanged = onCheckChanged,
        isMultiplyCheckedEnabled = true,
        modifier = Modifier.fillMaxWidth().padding(horizontal = padding)
    )

}