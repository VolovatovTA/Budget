package ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.title

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.title.IMeState
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.title.MeErrorState
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.title.MeLoadingState
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.title.MeSuccessState
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.budget.uikit.styles.UiKitStyles

@Composable
fun HomeTitleComponent(
    meState: IMeState
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
                Text(
                    text = "Oh. Hi, ${meState.name}!",
                    style = UiKitStyles.H1,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(horizontal = 30.dp)
                )
            }
            is MeErrorState -> {
                Text(
                    text = "Не удалось загрузить данные о вас...",
                    style = UiKitStyles.Body2,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(horizontal = 30.dp)
                )
            }
        }
    }
}