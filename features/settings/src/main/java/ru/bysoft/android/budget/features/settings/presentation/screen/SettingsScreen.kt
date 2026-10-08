package ru.bysoft.android.budget.features.settings.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ru.bysoft.android.budget.common.me_info.entity.DayOfWeek
import ru.bysoft.android.budget.common.me_info.entity.MeData
import ru.bysoft.android.budget.common.me_info.entity.SettingsData
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
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
import ru.bysoft.android.budget.uikit.styles.doubleCorner
import ru.bysoft.android.budget.uikit.styles.padding
import ru.bysoft.android.budget.features.settings.R

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
        state = state,
        onLogoutClick = viewModel::onLogoutClick,
        onDeleteAccountClick = viewModel::onDeleteAccountClick,
        onEditFirstDayOfWeek = viewModel::onEditFirstDayOfWeek,
        onEditCurrency = viewModel::onEditCurrency,
        onConfirm = viewModel::onConfirm
    )
}

@Composable
private fun SettingsScreenContent(
    state: SettingsState,
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    onEditFirstDayOfWeek: (DayOfWeek) -> Unit,
    onEditCurrency: (BudgetCurrencyEnum) -> Unit,
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
                var isLoadingSuccess by remember { mutableStateOf(state.meInfoData?.pictureUrl != null) }
                val modifier = Modifier
                    .padding(padding)
                    .size(100.dp)
                    .clip(RoundedCornerShape(doubleCorner))
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
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    Image(
                        imageVector = Person,
                        contentDescription = null,
                        modifier = modifier,
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(
                            color = UiKitColors.colors.neutral.`700`
                        )
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
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(padding)
                ) {

                    UiKitButton(
                        info = UiKitButtonInfo(
                            text = stringResource(R.string.logout),
                            size = ButtonSize.MEDIUM
                        ),
                        onClick = onLogoutClick,
                        modifier = Modifier.fillMaxWidth()
                    )

                    UiKitButton(
                        info = UiKitButtonInfo(
                            text = stringResource(R.string.delete_account),
                            size = ButtonSize.MEDIUM
                        ),
                        onClick = onDeleteAccountClick,
                        modifier = Modifier.fillMaxWidth()
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
    popupFieldState: CurrencyFieldState<BudgetCurrencyEnum>,
    onEdit: (BudgetCurrencyEnum) -> Unit
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
                email = "email",
                name = "name",
                pictureUrl = null,
//                pictureUrl = "https://lh3.googleusercontent.com/a/AGNmyxZMBP_uwqLUiYKRXkMjuHbInB5LeicqefblyJwdfg=s96-c",
                SettingsData("RUB", DayOfWeek.MONDAY),
                userId = "",
            ),
            dayOfWeekState = PopupFieldState(
                selectedValue = DayOfWeek.MONDAY,
                list = DayOfWeek.values().toList()
            )
        ),
        {}, {}, {}, {}, {}
    )
}