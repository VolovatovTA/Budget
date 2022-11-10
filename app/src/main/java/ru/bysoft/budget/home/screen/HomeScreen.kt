package ru.bysoft.budget.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.budget.uikit.components.listItem.UiKitListItem
import ru.bysoft.budget.uikit.icons.pack.*
import ru.bysoft.budget.uikit.styles.UiKitStyles
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(), backgroundColor = UiKitColors.white
    ) {
        Column(
            modifier = Modifier
//                .padding(it)
                .fillMaxSize()
                .background(UiKitColors.white)
        ) {
            Column(Modifier.shadow(2.dp)) {
                Text(
                    text = "Oh. Hi, Mark!",
                    style = UiKitStyles.H1,
                    modifier = Modifier
                        .padding(top = 100.dp)
                        .padding(horizontal = 30.dp)
                )

                Text(
                    text = "Баланс 40 000 руб",
                    style = UiKitStyles.H2,
                    modifier = Modifier
                        .padding(top = 17.dp)
                        .padding(horizontal = 30.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(vertical = 30.dp)
                        .padding(horizontal = 30.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
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

            SimpleList()

        }
    }
}



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
fun SimpleList(modifier: Modifier = Modifier) {
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