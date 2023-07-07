package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.title

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ru.bysoft.android.budget.common.me_info.entity.DayOfWeek
import ru.bysoft.android.budget.common.me_info.entity.MeData
import ru.bysoft.android.budget.common.me_info.entity.SettingsData
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.currency.getAvailableCurrency
import ru.bysoft.android.budget.currency.getBeautifulAmount
import ru.bysoft.android.budget.features.bottom_navigation.home.R
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.IMeState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeErrorState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeLoadingState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeSuccessState
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.components.textfield.UiKitCurrencyPopUpTextField
import ru.bysoft.android.budget.uikit.icons.pack.Person
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.corner
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding


@Composable
fun HomeTitleComponent(
    meState: IMeState,
    onSettingsClick: () -> Unit,
    onCurrencyChanged: (BudgetCurrencyEnum) -> Unit
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        when (meState) {
            is MeLoadingState -> {
                Row(
                    modifier = Modifier
                        .padding(top = padding)
                        .padding(horizontal = padding),
                ) {
                    UiKitShimmerComponent(
                        modifier = Modifier
                            .weight(1f)
                            .height(24.dp),
                        backgroundColor = UiKitColors.colors.neutral.`200`,
                        cornerRadius = corner
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            is MeSuccessState -> {
                TitleSuccessComponent(meState, onSettingsClick, onCurrencyChanged)
            }

            is MeErrorState -> {
                Text(
                    text = stringResource(R.string.error_while_loading_me_info),
                    style = UiKitTypography.TextMD.Regular,
                    modifier = Modifier
                        .padding(top = padding)
                        .padding(horizontal = padding)
                )
            }
        }
    }
}

@Composable
private fun TitleSuccessComponent(
    meState: MeSuccessState,
    onSettingsClick: () -> Unit,
    onCurrencyChanged: (BudgetCurrencyEnum) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = padding)
            .padding(horizontal = padding)
    ) {
        var isLoadingSuccess by remember { mutableStateOf(true) }
        val modifier = Modifier
            .size(24.dp)
            .clip(RoundedCornerShape(12.dp))
        if (isLoadingSuccess) {
            SubcomposeAsyncImage(
                model = meState.meData?.pictureUrl,
                modifier = modifier.clickable(onClick = onSettingsClick),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp)
                    )
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
                    .background(UiKitColors.colors.neutral.`200`)
                    .padding(7.dp)
                    .clickable(onClick = onSettingsClick),
            )
        }
        meState.meData?.name?.let {
            Text(
                text = it,
                style = UiKitTypography.TextMD.Regular,
                modifier = Modifier
                    .padding(start = halfPadding)
                    .weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        var expanded by remember { mutableStateOf(false) }
        val popUpList = CurrencyFieldState(
            selectedCurrency = meState.currency,
            list = getAvailableCurrency(),
        )
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.clickable { expanded = true }
            ) {
                popUpList.selectedCurrency?.let { t ->
                    UiKitCurrencyPopUpTextField(t)
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(UiKitColors.colors.surface.primary)
            ) {
                popUpList.list.forEach {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onCurrencyChanged(it)
                        },
                        modifier = Modifier.background(UiKitColors.colors.surface.primary)
                    ) {
                        UiKitCurrencyPopUpTextField(it)
                    }
                }
            }
            Text(
                text = getBeautifulAmount(meState.balance, meState.currency),
                style = UiKitTypography.TextMD.Regular,
                color = UiKitColors.colors.type.high,
            )
        }
    }
}

@Preview
@Composable
fun TitlePreview() {
    TitleSuccessComponent(
        meState = MeSuccessState(
            currency = BudgetCurrencyEnum.USD,
            balance = 100.0f,
            meData = MeData(
                "",
                "Тимофей Воловатов",
                "",
                SettingsData(
                    "",
                    DayOfWeek.MONDAY,
                ),
                ""
            ),
//            currency = "RUB"
        ),
        onSettingsClick = {},
        onCurrencyChanged = { }
    )
}

@Preview
@Composable
fun TitleErrorPreview() {
    HomeTitleComponent(MeErrorState, {}) {}
}

@Preview
@Composable
fun TitleWaitingPreview() {
    HomeTitleComponent(MeLoadingState, {}) {}
}