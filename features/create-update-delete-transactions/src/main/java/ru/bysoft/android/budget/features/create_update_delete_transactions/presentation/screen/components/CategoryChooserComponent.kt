package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.components

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import ru.bysoft.android.budget.features.create_update_delete_transactions.R
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen.padding
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.icons.UiKitIcons
import ru.bysoft.android.budget.uikit.icons.pack.Plus
import ru.bysoft.android.budget.uikit.styles.*

val HEIGHT_CATEGORY = 95.dp
val WIDTH_CATEGORY = 75.dp
val paddingBetweenElement = padding / 2

@Composable
fun CategoryChooserComponent(
    state: TransactionExpenseState,
    onClick: (CategoryPresentation) -> Unit,
    onClickAdd: () -> Unit,
) {
    when (state.categoryState) {
        is CategoryWaiting -> WaitingCategoryComponent()
        is CategoryError -> ErrorCategoryComponent()
        is CategorySuccess -> SuccessCategoryComponent(
            state = state.categoryState,
            onClick = onClick,
            onClickAdd = onClickAdd
        )
    }
}

@Composable
fun CategoryChooserComponent(
    state: TransactionIncomeState,
    onClick: (CategoryPresentation) -> Unit,
    onClickAdd: () -> Unit,
) {
    when (state.categoryState) {
        is CategoryWaiting -> WaitingCategoryComponent()
        is CategoryError -> ErrorCategoryComponent()
        is CategorySuccess -> SuccessCategoryComponent(
            state = state.categoryState,
            onClick = onClick,
            onClickAdd = onClickAdd
        )
    }
}

@Composable
private fun SuccessCategoryComponent(
    state: CategorySuccess,
    onClick: (CategoryPresentation) -> Unit,
    onClickAdd: () -> Unit,
) {
    if (state.listCategory.isNotEmpty()) {
        NotEmptySuccessCategoryComponent(state = state, onClick = onClick, onClickAdd = onClickAdd)
    }
}

@Composable
fun ShortSuccessCategoryComponent(
    text: String,
    onClick: () -> Unit
) {
    Row(
        Modifier.padding(horizontal = padding, vertical = padding / 2)
    ) {
        Text(
            text = text,
            style = UiKitTypography.TextXS.Regular,
            textAlign = TextAlign.Start,
            maxLines = 3,
            modifier = Modifier
                .weight(1f)
                .padding(paddingBetweenElement / 2)
        )
        UiKitButton(
            info = UiKitButtonInfo(
                text = stringResource(R.string.add_category2),
                size = ButtonSize.MEDIUM
            ),
            onClick = onClick
        )
    }

}

const val countRows = 2

@Composable
private fun NotEmptySuccessCategoryComponent(
    state: CategorySuccess,
    onClick: (CategoryPresentation) -> Unit,
    onClickAdd: () -> Unit,
) {
    val countRows = if (state.listCategory.size > 4) 2 else 1
    val list = state.listCategory.plus(CategoryAdd)
    LazyHorizontalGrid(
        rows = GridCells.Fixed(countRows),
        modifier = Modifier.height(countRows * HEIGHT_CATEGORY + padding * 3),
        verticalArrangement = Arrangement.spacedBy(padding),
        horizontalArrangement = Arrangement.spacedBy(padding),
        contentPadding = PaddingValues(start = padding, end = padding, bottom = halfPadding)
    ) {
        items(
            count = list.size,
            itemContent = { index ->
                when (val category = list[index]) {
                    is CategoryPresentation -> CategoryItem(category, onClick)
                    is CategoryAdd -> CategoryAddItem(onClickAdd)
                }
                Spacer(Modifier.width(if (index > list.size - countRows) halfPadding else 0.dp))

            }
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    locale = "ru"
)
@Composable
fun PreviewCategory() {
    NotEmptySuccessCategoryComponent(
        CategorySuccess(
            listOf(
                CategoryPresentation("Card", "dshb", false, "", "RUB"),
                CategoryPresentation("Card", "dshfldjb", true, "", "GEL"),
                CategoryPresentation("Card", "dsh", true, "", "GEL"),
                CategoryPresentation("Card", "dshb df dff", true, "", "GEL"),
                CategoryPresentation("Card", "dshb dfdfdfdfdfdf", true, "", "GEL"),
            )
        ),
        {},
        {}
    )

    WaitingCategoryComponent()

}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0E1216,
)
@Composable
fun PreviewCategoryNight() {
    NotEmptySuccessCategoryComponent(
        CategorySuccess(
            listOf(
                CategoryPresentation("Card", "dshb", false, "", "RUB"),
                CategoryPresentation("Card", "dshfldjb", true, "", "GEL"),
                CategoryPresentation("Card", "dsh", true, "", "GEL"),
                CategoryPresentation("Card", "dshb df dff", true, "", "GEL"),
                CategoryPresentation("Card", "dshb dfdfdfdfdfdf", true, "", "GEL"),
            )
        ),
        {},
        {}
    )

    WaitingCategoryComponent()

}

@Composable
private fun CategoryAddItem(
    onClick: () -> Unit
) {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    val borderColor = UiKitColors.colors.type.high
    Surface(
        shape = RoundedCornerShape(corner),
        color = UiKitColors.card(isSelected = false),
        modifier = Modifier
            .size(WIDTH_CATEGORY, HEIGHT_CATEGORY),
        elevation = 3.dp
    ) {
        Column(
            Modifier
                .clickable {
                    onClick()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.add_category1),
                style = UiKitTypography.TextXS.Regular,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(quarterPadding)
            )
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .drawBehind {
                        drawCircle(color = borderColor, style = stroke)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Plus,
                    contentDescription = null,
                    tint = UiKitColors.colors.type.high
                )
            }
            Text(
                text = stringResource(R.string.add_category2),
                style = UiKitTypography.TextXS.Regular,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(quarterPadding)
            )

        }
    }
}

@Composable
private fun CategoryItem(
    category: CategoryPresentation,
    onClick: (CategoryPresentation) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(corner),
        color = UiKitColors.card(category.isChosen),
        modifier = Modifier
            .height(HEIGHT_CATEGORY),
        elevation = 3.dp
    ) {
        Column(
            Modifier
                .clickable {
                    onClick(category)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                UiKitIcons.getByName(category.iconName)?.let {
                    Icon(
                        it,
                        contentDescription = null,
                        tint = UiKitColors.colors.type.high
                    )
                }
            }
            Text(
                text = category.name,
                style = UiKitTypography.TextXS.Regular,
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier
                    .widthIn(max = WIDTH_CATEGORY)
                    .padding(horizontal = halfPadding)
            )
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = category.currency,
                    style = UiKitTypography.TextXS.Regular,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(horizontal = halfPadding, vertical = halfPadding)
                        .clip(RoundedCornerShape(doubleCorner))
                        .background(UiKitColors.colors.primary.`600`)
                        .padding(horizontal = halfPadding, vertical = quarterPadding),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun WaitingCategoryComponent() {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(countRows),
        modifier = Modifier.height(countRows * HEIGHT_CATEGORY + padding * 3),
        verticalArrangement = Arrangement.spacedBy(padding),
        horizontalArrangement = Arrangement.spacedBy(padding),
        contentPadding = PaddingValues(start = padding, end = padding, bottom = halfPadding)
    ) {
        val countElement = 10
        items(countElement) {
            UiKitShimmerComponent(
                Modifier
                    .size(WIDTH_CATEGORY, HEIGHT_CATEGORY)
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
            .padding(vertical = padding, horizontal = padding)
    )
}