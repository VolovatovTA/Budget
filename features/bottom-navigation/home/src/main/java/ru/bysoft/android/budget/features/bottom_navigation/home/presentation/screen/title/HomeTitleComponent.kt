package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.title

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.IMeState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeErrorState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeLoadingState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeSuccessState
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.styles.UiKitStyles
import ru.bysoft.android.budget.features.bottom_navigation.home.R
import ru.bysoft.android.budget.uikit.icons.pack.Filters


@Composable
fun HomeTitleComponent(
    meState: IMeState,
    onSettingsClick: () -> Unit
) {
    Box(
        modifier = Modifier.height(50.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        when (meState) {
            is MeLoadingState -> {
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(horizontal = 30.dp),
                ) {
                    UiKitShimmerComponent(
                        modifier = Modifier.weight(1f),
                        backgroundColor = UiKitColors.colors.col4_inactive,
                        cornerRadius = 15.dp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            is MeSuccessState -> {
                TitleSuccessComponent(meState, onSettingsClick)
            }
            is MeErrorState -> {
                Text(
                    text = stringResource(R.string.error_while_loading_me_info),
                    style = UiKitStyles.Body2,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(horizontal = 30.dp)
                )
            }
        }
    }
}

@Composable
private fun TitleSuccessComponent(meState: MeSuccessState, onSettingsClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = meState.name,
            style = UiKitStyles.H2,
            modifier = Modifier
//                .padding(top = 20.dp)
                .padding(horizontal = 30.dp)
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(onClick = onSettingsClick) {
                Icon(imageVector = Filters, contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun TitlePreview(){
    TitleSuccessComponent(
        meState = MeSuccessState(
            name = "Name",
//            currency = "RUB"
        ),
        onSettingsClick = {}
    )
}