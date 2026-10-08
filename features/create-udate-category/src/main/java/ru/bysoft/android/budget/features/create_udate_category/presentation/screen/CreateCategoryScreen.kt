package ru.bysoft.android.budget.features.create_udate_category.presentation.screen

import ru.bysoft.android.budget.features.create_udate_category.presentation.mapper.toIconPack
import ru.bysoft.android.budget.currency.getAvailableCurrency
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.bysoft.android.budget.common.util.CategoryTypeEnum
import ru.bysoft.android.budget.common.util.PeriodState
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.features.create_udate_category.R
import ru.bysoft.android.budget.features.create_udate_category.navigation.CreateCategoryNavInfo
import ru.bysoft.android.budget.features.create_udate_category.presentation.components.ButtonComponent
import ru.bysoft.android.budget.features.create_udate_category.presentation.components.CreateUpdateCategoryTextField
import ru.bysoft.android.budget.features.create_udate_category.presentation.entity.CreateUpdateCategoryState
import ru.bysoft.android.budget.features.create_udate_category.presentation.viewmodels.ICreateCategoryViewModel
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.expandablecontent.VerticalExpandableContent
import ru.bysoft.android.budget.uikit.components.icon_component.UiKitIconsComponent
import ru.bysoft.android.budget.uikit.components.rowtab.UiKitRowTab
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitRowTabState
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitTabInfo
import ru.bysoft.android.budget.uikit.components.textfield.UiKitCurrencyPopUpTextField
import ru.bysoft.android.budget.uikit.components.textfield.UiKitTextFieldWithPopUpAndCurrency
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.halfCorner
import ru.bysoft.android.budget.uikit.styles.padding
import ru.bysoft.android.budget.uikit.templates.topBar

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
        topBar = topBar(
            title = R.string.create_category_title,
            onBackClick = viewModel::back,
        )

    ) { paddingValues ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(padding),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(padding)
        ) {
            val rowTabs = listOf(
                CategoryTypeEnum.EXPENSE,
                CategoryTypeEnum.INCOME,
            )

            item {
                UiKitRowTab(
                    startState = UiKitRowTabState(
                        rowTabs.map { type ->
                            UiKitTabInfo(
                                text = type.text,
                                isChecked = type == state.typeCategory
                            )
                        }
                    ),
                    onCheckChanged = { _, position ->
                        viewModel.setCategoryType(rowTabs[position])
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                CreateUpdateCategoryTextField(
                    state = state.nameTextState,
                    onTextChange = viewModel::onNameChanged,
                    label = stringResource(R.string.text_field_name_category_label),
                    type = KeyboardType.Text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) },
                )
            }
            item {
                VerticalExpandableContent(isCollapsed = state.typeCategory != CategoryTypeEnum.EXPENSE) {
                    CreateCategoryLimitComponent(
                        state = state,
                        onAmountChanged = viewModel::onAmountChanged,
                        onPeriodSelected = viewModel::onPeriodSelected,
                        focusManager = focusManager,
                        onCurrencySelected = viewModel::onCurrencySelected
                    )
                }
            }

            item {
                UiKitIconsComponent(
                    pack = state.typeCategory.toIconPack(),
                    viewModel::onIconSelected,
                    state.iconState
                )
            }

            item {
                ButtonComponent(
                    onClickCreateUpdate = viewModel::onClickCreate,
                    state = state,
                    text = stringResource(R.string.btn_finish_create_text)
                )
            }
        }
    }
}

@Composable
fun CreateCategoryLimitComponent(
    state: CreateUpdateCategoryState,
    onAmountChanged: (String) -> Unit,
    onPeriodSelected: (PeriodState) -> Unit,
    focusManager: FocusManager,
    onCurrencySelected: (BudgetCurrencyEnum) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(padding)) {
        Text(
            text = stringResource(R.string.text_limit_description),
            style = UiKitTypography.TextMD.Regular,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(padding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UiKitTextFieldWithPopUpAndCurrency(
                state = state.amountTextState,
                onValueChange = onAmountChanged,
                label = stringResource(R.string.text_field_limit_label),
                inputType = KeyboardType.Number,
                keyboardActions = KeyboardActions { focusManager.clearFocus() },
                currencyPopUpList = state.currencyFieldState,
                onSelectCurrency = onCurrencySelected,
                popUpList = state.periodState,
                onSelectPopupItem = onPeriodSelected,
                modifier = Modifier
                    .fillMaxWidth(1f),
                popupItem = {
                    Text(
                        text = stringResource(id = it?.textToShow ?: R.string.undefined_text),
                        style = UiKitTypography.TextSM.Regular,
                        modifier = Modifier
                            .padding(padding)
                            .clip(RoundedCornerShape(halfCorner))
                    )
                },
                currencyItem = {
                    it?.let {
                        UiKitCurrencyPopUpTextField(it.displayName, it.flag)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun UpdateScreenPreview() {
    CreateCategoryScreen(
        object : ICreateCategoryViewModel {
            override fun onClickCreate() {
                TODO("Not yet implemented")
            }

            override fun initNavParams(typeCategory: CreateCategoryNavInfo?) {
                TODO("Not yet implemented")
            }

            override val state: StateFlow<CreateUpdateCategoryState>
                get() = MutableStateFlow(
                    CreateUpdateCategoryState(
                        currencyFieldState = CurrencyFieldState(
                            selectedCurrency = BudgetCurrencyEnum.GEL,
                            list = getAvailableCurrency(),
                        )
                    )
                )

            override fun onCurrencySelected(currency: BudgetCurrencyEnum) {
                TODO("Not yet implemented")
            }

            override fun onPeriodSelected(newPeriod: PeriodState) {
                TODO("Not yet implemented")
            }

            override fun onNameChanged(newName: String) {
                TODO("Not yet implemented")
            }

            override fun onIconSelected(iconName: String?) {
                TODO("Not yet implemented")
            }

            override fun onAmountChanged(newAmount: String) {
                TODO("Not yet implemented")
            }

            override fun setCategoryType(type: CategoryTypeEnum) {
                TODO("Not yet implemented")
            }

            override fun back() {
                TODO("Not yet implemented")
            }
        }
    )
}
