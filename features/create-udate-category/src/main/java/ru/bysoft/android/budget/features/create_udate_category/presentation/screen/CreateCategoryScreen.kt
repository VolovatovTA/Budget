package ru.bysoft.android.budget.features.create_udate_category.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.common.util.CategoryTypeEnum
import ru.bysoft.android.budget.common.util.PeriodState
import ru.bysoft.android.budget.features.create_udate_category.R
import ru.bysoft.android.budget.features.create_udate_category.presentation.components.ButtonComponent
import ru.bysoft.android.budget.features.create_udate_category.presentation.components.CreateUpdateCategoryTextField
import ru.bysoft.android.budget.features.create_udate_category.presentation.entity.CreateUpdateCategoryState
import ru.bysoft.android.budget.features.create_udate_category.presentation.viewmodels.ICreateCategoryViewModel
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.currencyfield.UiKitCurrencyPopUp
import ru.bysoft.android.budget.uikit.components.currencyfield.UiKitPopUp
import ru.bysoft.android.budget.uikit.components.icon_component.UiKitIconsComponent
import ru.bysoft.android.budget.uikit.components.rowtab.UiKitRowTab
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitRowTabState
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitTabInfo
import ru.bysoft.android.budget.uikit.icons.pack.ArrowLeft
import ru.bysoft.android.budget.uikit.styles.UiKitTypography

@Composable
fun CreateCategoryScreen(
    viewModel: ICreateCategoryViewModel
) {
    val state = viewModel.state.collectAsState().value
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }
    state.toastText?.let {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        backgroundColor = UiKitColors.colors.surface.primary,
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
                    text = stringResource(R.string.create_category_title), style = UiKitTypography.DisplayXS.Regular
                )
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
                            text = stringResource(type.text),
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
                label = stringResource(R.string.text_field_name_category_label),
                type = KeyboardType.Text,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
            )
            if (state.typeCategory == CategoryTypeEnum.EXPENSE) {
                CreateCategoryLimitComponent(
                    state = state,
                    onAmountChanged = viewModel::onAmountChanged,
                    onPeriodSelected = viewModel::onPeriodSelected,
                    focusManager = focusManager
                )
            }

            UiKitCurrencyPopUp(state.currencyFieldState, viewModel::onCurrencySelected)
            UiKitIconsComponent(viewModel::onIconSelected, state.iconState)
            Spacer(modifier = Modifier.height(40.dp))

            ButtonComponent(
                onClickCreateUpdate = viewModel::onClickCreate,
                state = state,
                text = stringResource(R.string.btn_finish_create_text)
            )
        }
    }
}

@Composable
fun CreateCategoryLimitComponent(
    state: CreateUpdateCategoryState,
    onAmountChanged: (String) -> Unit,
    onPeriodSelected: (PeriodState) -> Unit,
    focusManager: FocusManager
) {
    Column {
        Text(
            text = stringResource(R.string.text_limit_description),
            style = UiKitTypography.TextMD.Regular,
            modifier = Modifier.fillMaxWidth().padding(top = 15.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            CreateUpdateCategoryTextField(
                state = state.amountTextState,
                onTextChange = onAmountChanged,
                label = stringResource(R.string.text_field_limit_label),
                type = KeyboardType.Number,
                modifier = Modifier
                    .weight(1f),
                keyboardActions = KeyboardActions { focusManager.clearFocus() }
            )
            Spacer(modifier = Modifier.width(10.dp))
            UiKitPopUp(
                info = state.periodState,
                onClickItem = onPeriodSelected,
                modifier = Modifier
                    .height(57.dp)
                    .weight(1f)
            ) { periodState ->
                if (periodState != null) {
                    Box(
                        modifier = Modifier.padding(start = 15.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = stringResource(id = periodState.textToShow),
                            style = UiKitTypography.TextMD.Regular
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.undefined_text),
                            style = UiKitTypography.TextMD.Regular
                        )
                    }
                }
            }
        }
    }
}