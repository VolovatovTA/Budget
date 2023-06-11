package ru.bysoft.android.budget.features.settings.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ru.bysoft.android.budget.common.me_info.entity.DayOfWeek
import ru.bysoft.android.budget.common.me_info.entity.MeData
import ru.bysoft.android.budget.common.me_info.entity.SettingsData
import ru.bysoft.android.budget.common.util.BudgetCurrency
import ru.bysoft.android.budget.features.settings.presentation.SettingsViewModel
import ru.bysoft.android.budget.features.settings.presentation.entity.SettingsState
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.currencyfield.UiKitPopUp
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.PopupFieldState
import ru.bysoft.android.budget.uikit.icons.pack.Person
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.settings.R

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.toastState.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    SettingsScreenContent(
        state,
        viewModel::onLogoutClick,
        viewModel::onEditFirstDayOfWeek,
        viewModel::onEditCurrency,
        viewModel::onConfirm
    )
}

@Composable
private fun SettingsScreenContent(
    state: SettingsState,
    onLogoutClick: () -> Unit,
    onEditFirstDayOfWeek: (DayOfWeek) -> Unit,
    onEditCurrency: (BudgetCurrency) -> Unit,
    onConfirm: () -> Unit
) {
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        backgroundColor = UiKitColors.colors.surface.primary,
        topBar = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
            ) {
                if (state.isDataChanged && !state.isLoading) {
                    Text(
                        text = stringResource(id = R.string.confirm),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable(onClick = onConfirm),
                        style = UiKitTypography.TextMD.Regular
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var isLoadingSuccess by remember { mutableStateOf(true) }
                val modifier = Modifier
                    .padding(30.dp)
                    .size(100.dp)
                    // не работает почему-то
                    .clip(CircleShape)
                if (isLoadingSuccess) {
                    SubcomposeAsyncImage(
                        model = state.meInfoData?.pictureUrl,
                        modifier = modifier,
                        loading = {
                            CircularProgressIndicator()
                        },
                        onError = {
                            isLoadingSuccess = false
                        },
                        contentDescription = stringResource(R.string.icon_description),
                        contentScale = androidx.compose.ui.layout.ContentScale.FillBounds
                    )
                } else {
                    Icon(
                        imageVector = Person,
                        contentDescription = null,
                        modifier = modifier
                    )
                }

                DataElement(R.string.your_email, state.meInfoData?.email)
                DataElement(R.string.your_name, state.meInfoData?.name)
                CurrencyField(
                    R.string.your_currency,
                    state.currencyFieldState,
                    onEditCurrency
                )
                DataElementWithEditingByPopUp(
                    R.string.your_first_day_of_week,
                    state.dayOfWeekState,
                    onEditFirstDayOfWeek
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 30.dp)
                ) {
                    UiKitButton(
                        info = UiKitButtonInfo(
                            text = stringResource(R.string.logout),
                            size = ButtonSize.MEDIUM
                        ),
                        onClick = onLogoutClick,
                    )
                }
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp),
                        strokeCap = StrokeCap.Round,
                        strokeWidth = 5.dp,
                        color = UiKitColors.colors.neutral.`800`
                    )
                }
            }
        }
    )
}

@Composable
private fun DataElement(key: Int, value: String?) {
    value?.let {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 5.dp)
        ) {
            Text(
                text = stringResource(id = key),
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                style = UiKitTypography.TextXS.Regular
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = value,
                    modifier = Modifier
                        .padding(start = 10.dp),
                    style = UiKitTypography.TextMD.Regular
                )
            }
        }
    }
}

@Composable
private fun CurrencyField(
    key: Int,
    popupFieldState: CurrencyFieldState,
    onEdit: (BudgetCurrency) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 5.dp)
    ) {
        Text(
            text = stringResource(id = key),
            modifier = Modifier
                .align(Alignment.CenterVertically),
            style = UiKitTypography.TextXS.Regular
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            UiKitPopUp(
                info = PopupFieldState(
                    selectedValue = popupFieldState.selectedCurrency,
                    list = popupFieldState.list
                ),
                onClickItem = onEdit,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .height(20.dp)
                    .wrapContentWidth(Alignment.End)
            ) {
                if (it != null) {
                    Text(
                        text = it.displayName,
                        modifier = Modifier
                            .padding(start = 10.dp),
                        style = UiKitTypography.TextMD.Regular
                    )
                } else {
                    Text(
                        text = stringResource(R.string.not_loaded),
                        modifier = Modifier
                            .padding(start = 10.dp),
                        style = UiKitTypography.TextMD.Regular
                    )
                }
            }
        }
    }
}

@Composable
private fun DataElementWithEditingByPopUp(
    key: Int,
    popupFieldState: PopupFieldState<DayOfWeek>,
    onEdit: (DayOfWeek) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 5.dp)
    ) {
        Text(
            text = stringResource(id = key),
            modifier = Modifier
                .align(Alignment.CenterVertically),
            style = UiKitTypography.TextXS.Regular
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            UiKitPopUp(
                info = PopupFieldState(
                    selectedValue = popupFieldState.selectedValue,
                    list = popupFieldState.list
                ),
                onClickItem = onEdit,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .height(20.dp)
                    .wrapContentWidth(Alignment.End)
            ) {
                if (it != null) {
                    Text(
                        text = stringResource(it.resId),
                        modifier = Modifier
                            .padding(start = 10.dp),
                        style = UiKitTypography.TextMD.Regular
                    )
                } else {
                    Text(
                        text = stringResource(R.string.not_loaded),
                        modifier = Modifier
                            .padding(start = 10.dp),
                        style = UiKitTypography.TextMD.Regular
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    SettingsScreenContent(
        SettingsState(
            MeData(
                "email",
                "name",
                "https://lh3.googleusercontent.com/a/AGNmyxZMBP_uwqLUiYKRXkMjuHbInB5LeicqefblyJwdfg=s96-c",
                SettingsData("RUB", DayOfWeek.MONDAY),
                "",
            ),
            dayOfWeekState = PopupFieldState(
                selectedValue = DayOfWeek.MONDAY,
                list = DayOfWeek.values().toList()
            )
        ),
        {}, {}, {}, {}
    )
}