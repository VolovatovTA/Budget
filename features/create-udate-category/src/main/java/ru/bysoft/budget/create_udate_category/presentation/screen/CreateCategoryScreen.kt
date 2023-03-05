package ru.bysoft.budget.create_udate_category.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.create_udate_category.presentation.viewmodels.ICreateCategoryViewModel
import ru.bysoft.budget.create_udate_category.presentation.components.ButtonComponent
import ru.bysoft.budget.create_udate_category.presentation.components.CreateUpdateCategoryTextField
import ru.bysoft.budget.create_udate_category.presentation.components.IconsComponent
import ru.bysoft.budget.create_udate_category.presentation.entity.CategoryTypeEnum
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.budget.uikit.components.currecyfield.UiKitCurrencyPopUp
import ru.bysoft.budget.uikit.components.currecyfield.UiKitPopUp
import ru.bysoft.budget.uikit.components.rowtab.UiKitRowTab
import ru.bysoft.budget.uikit.components.rowtab.entity.UiKitRowTabState
import ru.bysoft.budget.uikit.components.rowtab.entity.UiKitTabInfo
import ru.bysoft.budget.uikit.icons.pack.ArrowLeft
import ru.bysoft.budget.uikit.styles.UiKitStyles

@Composable
fun CreateCategoryScreen(
    viewModel: ICreateCategoryViewModel
) {
    val state = viewModel.state.collectAsState().value
    state.toastText?.let {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                Icon(
                    imageVector = ArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = viewModel::back)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Создание категории", style = UiKitStyles.H2
                )
                Spacer(modifier = Modifier.width(15.dp))

                Spacer(modifier = Modifier.width(15.dp))

            }
        }

    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 30.dp)
        ) {
            val rowTabs = listOf(
                CategoryTypeEnum.EXPENSE,
                CategoryTypeEnum.INCOME,
            )

            UiKitRowTab(
                startState = UiKitRowTabState(
                    rowTabs.map { type ->
                        UiKitTabInfo(
                            info = UiKitButtonInfo(
                                type.text,
                                type = ButtonType.SMALL
                            ),
                            isChecked = type == state.typeCategory
                        )
                    }
                ),
                onCheckChanged = { _, position ->
                    viewModel.setCategoryType(rowTabs[position])
                }
            )
            CreateUpdateCategoryTextField(
                state = state.nameTextState,
                onTextChange = viewModel::onNameChanged,
                label = "Имя новой категории",
                type = KeyboardType.Text,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                CreateUpdateCategoryTextField(
                    state = state.amountTextState,
                    onTextChange = viewModel::onAmountChanged,
                    label = "Можно назначить лимит",
                    type = KeyboardType.Number,
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                UiKitPopUp(
                    info = state.periodState,
                    onClickItem = viewModel::onPeriodSelected,
                    modifier = Modifier
                        .height(52.dp)
                        .weight(1f)
                ) { periodState ->
                    if (periodState != null) {
                        Box(
                            modifier = Modifier.padding(start = 15.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = periodState.textToShow,
                                style = UiKitStyles.Body2
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Неопределено",
                                style = UiKitStyles.Body2
                            )
                        }
                    }
                }
            }

            UiKitCurrencyPopUp(state.currencyFieldState, viewModel::onCurrencySelected)
            IconsComponent(viewModel::onIconSelected, state.iconState)
            Spacer(modifier = Modifier.height(40.dp))

            ButtonComponent(viewModel::onClickCreate, state, "Создать новую категорию")
        }
    }
}