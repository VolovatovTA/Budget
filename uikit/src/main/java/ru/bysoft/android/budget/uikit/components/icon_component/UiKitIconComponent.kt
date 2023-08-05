package ru.bysoft.android.budget.uikit.components.icon_component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import ru.bysoft.android.budget.common.util.CategoryTypeEnum
import ru.bysoft.android.budget.common.util.onNull
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.icons.UiKitIcons
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.corner
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding
import ru.bysoft.android.budget.uikit.theme.BudgetTheme


data class UiKitIconState(
    val iconName: String?
)

val iconSize = 50.dp

@Composable
fun UiKitIconsComponent(
    type: CategoryTypeEnum? = null,
    onClick: (String?) -> Unit,
    selectedIcon: UiKitIconState
) {

    val listIcons =
        // add null icon (= without icon)
        listOf<UiKitIcons.IIcons?>(null) +
                type?.let {
                    UiKitIcons.getCategoriesIcons(type)
                }.onNull {
                    UiKitIcons.getWalletIcons()
                }
    val rowsCount = when (listIcons.size) {
        in (0..7) -> 1
        in (8..14) -> 2
        else -> 3
    }
    LazyHorizontalGrid(
        rows = GridCells.Adaptive(iconSize),
        // 1.dp because Adaptive doesn't work with 0.dp
        modifier = Modifier.height(rowsCount * iconSize + halfPadding * (rowsCount - 1) + 1.dp),
        verticalArrangement = Arrangement.spacedBy(halfPadding),
        horizontalArrangement = Arrangement.spacedBy(halfPadding)
    ) {
        items(listIcons.size) {
            val icon = listIcons[it]
            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon.id),
                    tint = UiKitColors.colors.type.high,
                    contentDescription = null,
                    modifier = Modifier
                        .size(iconSize)
                        .clip(RoundedCornerShape(corner))
                        .clickable(
                            remember { MutableInteractionSource() },
                            indication = null
                        ) { onClick(icon.nameForBack) }
                        .background(
                            UiKitColors.card(icon.nameForBack == selectedIcon.iconName)
                        )
                        .padding(halfPadding)
                )
            } else {
                Text(
                    text = stringResource(R.string.without_icon),
                    style = UiKitTypography.TextXS.Regular,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .size(iconSize)
                        .clip(RoundedCornerShape(corner))
                        .clickable(
                            remember { MutableInteractionSource() },
                            indication = null
                        ) { onClick(null) }
                        .background(
                            UiKitColors.card(selectedIcon.iconName == null)
                        )
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
            }
        }
    }
}


@BothThemePreview
@Composable
fun UiKitIconsComponentPreview() {
    BudgetTheme {
        Column(verticalArrangement = Arrangement.spacedBy(padding)) {
            UiKitIconsComponent(
                type = CategoryTypeEnum.EXPENSE,
                onClick = {},
                selectedIcon = UiKitIconState(iconName = "activity")
            )
            UiKitIconsComponent(
                type = CategoryTypeEnum.INCOME,
                onClick = {},
                selectedIcon = UiKitIconState(iconName = null)
            )
            UiKitIconsComponent(
                type = null,
                onClick = {},
                selectedIcon = UiKitIconState(iconName = null)
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    showBackground = true,
    backgroundColor = 0xFF0E1216,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
annotation class BothThemePreview
