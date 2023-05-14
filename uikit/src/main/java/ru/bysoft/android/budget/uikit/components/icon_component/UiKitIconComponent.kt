package ru.bysoft.android.budget.uikit.components.icon_component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.uikit.components.avatar.UiKitAvatar
import ru.bysoft.android.budget.uikit.icons.UiKitIcons
import ru.bysoft.android.budget.uikit.styles.UiKitStyles


data class UiKitIconState(
    val iconName: String?
)

@Composable
fun UiKitIconsComponent(onClick: (String?) -> Unit, selectedIcon: UiKitIconState) {
    val listIcons = UiKitIcons.getCategories().map { it.second }.plus(null)
    LazyVerticalGrid(
        columns = GridCells.Adaptive(45.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
    ) {
        items(listIcons.size) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val icon = listIcons[it]
                if (icon != null) {
                    UiKitAvatar(
                        icon = icon,
                        backgroundColor =
                        if (icon.name == selectedIcon.iconName) Color(0x22000000) else
                            Color.Transparent,
                        onClick = { onClick(icon.name) },
                        rippleEnabled = false,
                        elevation = 0.dp
                    )
                } else {
                    Text(
                        text = stringResource(R.string.without_icon),
                        style = UiKitStyles.Caption,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable(
                                remember { MutableInteractionSource() },
                                indication = null
                            ) { onClick(null) }
                            .background(
                                if (selectedIcon.iconName == null) Color(0x22000000)
                                else Color.Transparent
                            )
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}