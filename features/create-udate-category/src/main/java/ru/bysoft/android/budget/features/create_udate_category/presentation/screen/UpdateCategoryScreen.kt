package ru.bysoft.android.budget.features.create_udate_category.presentation.screen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.features.create_udate_category.R
import ru.bysoft.android.budget.features.create_udate_category.presentation.viewmodels.IUpdateCategoryViewModel
import ru.bysoft.android.budget.features.create_udate_category.presentation.components.ButtonComponent
import ru.bysoft.android.budget.features.create_udate_category.presentation.components.CreateUpdateCategoryTextField
import ru.bysoft.android.budget.features.create_udate_category.presentation.components.IconsComponent
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.currecyfield.UiKitCurrencyPopUp
import ru.bysoft.android.budget.uikit.components.currecyfield.UiKitPopUp
import ru.bysoft.android.budget.uikit.icons.pack.ArrowLeft
import ru.bysoft.android.budget.uikit.icons.pack.Delete
import ru.bysoft.android.budget.uikit.styles.UiKitStyles

@Composable
fun UpdateCategoryScreen(
    viewModel: IUpdateCategoryViewModel, id: String
) {
    LaunchedEffect(Unit) { viewModel.initId(id) }
    val state = viewModel.state.collectAsState().value
    state.toastText?.let {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(topBar = {
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
                text = stringResource(R.string.update_category_title), style = UiKitStyles.H2
            )
            Spacer(modifier = Modifier.width(15.dp))

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Delete,
                    contentDescription = null,
                    tint = UiKitColors.colors.red,
                    modifier = Modifier.clickable(onClick = viewModel::delete)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))

        }
    }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 30.dp)
        ) {
            CreateUpdateCategoryTextField(
                state = state.nameTextState,
                onTextChange = viewModel::onNameChanged,
                label = stringResource(R.string.text_field_name_category_label),
                type = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth()
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
                    label = stringResource(R.string.text_field_limit_label),
                    type = KeyboardType.Number,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                UiKitPopUp(
                    info = state.periodState,
                    onClickItem = viewModel::onPeriodSelected,
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
                                text = stringResource(periodState.textToShow),
                                style = UiKitStyles.Body2
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.undefined_text), style = UiKitStyles.Body2
                            )
                        }
                    }
                }
            }

            UiKitCurrencyPopUp(state.currencyFieldState, viewModel::onCurrencySelected)
            IconsComponent(viewModel::onIconSelected, state.iconState)
            Spacer(modifier = Modifier.height(40.dp))

            ButtonComponent(viewModel::onClickSave, state, stringResource(R.string.btn_finish_update_text))
        }
    }
}