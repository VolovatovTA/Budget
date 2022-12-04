package ru.bysoft.budget.home.presentation.screen.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo

@Composable
fun HomeFiltersComponent() {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
//        IconToggleButton(checked = , onCheckedChange = ) {
//
//        }
        UiKitButton(
            UiKitButtonInfo(
                text = "Расход", type = ButtonType.SMALL
            ),
            Modifier.padding(end = 15.dp)

        )
        UiKitButton(
            UiKitButtonInfo(
                text = "Переводы", type = ButtonType.SMALL
            ),
            Modifier
                .padding(end = 15.dp),
        )
        UiKitButton(
            UiKitButtonInfo(
                text = "Доход",
                type = ButtonType.SMALL,
            ),
        )
    }
}