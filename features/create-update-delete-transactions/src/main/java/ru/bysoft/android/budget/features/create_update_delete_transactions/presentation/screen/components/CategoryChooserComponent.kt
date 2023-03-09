package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.HEIGHT_ELEMENT
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.MAX_HEIGHT
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.avatar.UiKitAvatar
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.icons.UiKitIcons
import ru.bysoft.android.budget.uikit.styles.UiKitStyles
import ru.bysoft.android.budget.features.create_update_delete_transactions.R
@Composable
fun CategoryChooserComponent(state: TransactionExpenseState, onClick: (CategoryPresentation) -> Unit) {
    when (state.categoryState) {
        is CategoryWaiting -> WaitingCategoryComponent()
        is CategoryError -> ErrorCategoryComponent()
        is CategorySuccess -> SuccessCategoryComponent(state.categoryState, onClick)
    }
}

@Composable
fun CategoryChooserComponent(state: TransactionIncomeState, onClick: (CategoryPresentation) -> Unit) {
    when (state.categoryState) {
        is CategoryWaiting -> WaitingCategoryComponent()
        is CategoryError -> ErrorCategoryComponent()
        is CategorySuccess -> SuccessCategoryComponent(state.categoryState, onClick)
    }
}

@Composable
private fun SuccessCategoryComponent(
    state: CategorySuccess,
    onClick: (CategoryPresentation) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 10.dp)
            .heightIn(max = 300.dp)
    ) {
        items(state.listCategory.size) {
            val category = state.listCategory[it]
            val backgroundColor =
                if (state.listCategory[it].isChosen) UiKitColors.colors.col4_inactive
                else UiKitColors.colors.light
            Row(
                Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundColor)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onClick(category) },
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                UiKitAvatar(
                    icon = UiKitIcons.getByName(category.iconName),
                    backgroundColor = backgroundColor,
                    elevation = 0.dp,
                    rippleEnabled = false,
                    onClick = { onClick(category) },
                )
                Text(
                    text = category.name,
                    style = UiKitStyles.Caption,
                    textAlign = TextAlign.Start,
                    maxLines = 3,
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                )
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = category.currency,
                        style = UiKitStyles.Caption,
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun WaitingCategoryComponent() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 10.dp)
            .heightIn(max = MAX_HEIGHT)
    ) {
        items(7) {
            UiKitShimmerComponent(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .height(HEIGHT_ELEMENT)
            )
        }

    }
}

@Composable
private fun ErrorCategoryComponent() {
    Text(
        text = stringResource(R.string.error_loading_categories),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 30.dp)
    )
}