package ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.uikit.components.listItem.UiKitListItem
import ru.bysoft.budget.uikit.icons.pack.*
import java.util.*

val list = listOf(
    ArrowLeft,
    Filters,
    Beach,
    Minus,
    Oil,
    Health,
    Rest,
    Food,
    Card,
    CheckMark,
    Education,
    Heart,
    House,
    Edit,
    Recycle,
    Search,
    Statistic,
    Multiply,
    Copy,
    Plus,
    ArrowDown,
    ArrowUp,
    ArrowRight,
    Person,
    Shield,
    Clear,
    Delete,
    Internet,
    Menu,
    Photo,
    Tele,
    Calendar,
)

@Composable
fun HomeTransactionsComponent(modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(list.size) {
            UiKitListItem(
                title = list[it].name,
                subTitle = Date(System.currentTimeMillis()).toLocaleString(),
                icon = list[it],
                modifier = Modifier
                    .clickable { }
                    .padding(vertical = 10.dp, horizontal = 30.dp),
                count = "-1900руб"
            )
        }
    }
}