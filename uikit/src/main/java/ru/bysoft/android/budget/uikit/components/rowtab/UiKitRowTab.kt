package ru.bysoft.android.budget.uikit.components.rowtab

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitRowTabState
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitTabInfo
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding
import ru.bysoft.android.budget.uikit.styles.quarterPadding

@Composable
fun UiKitRowTab(
    startState: UiKitRowTabState,
    onCheckChanged: (Boolean, Int) -> Unit,
    modifier: Modifier = Modifier,
    isMultiplyCheckedEnabled: Boolean = false
) {
    var innerState by remember { mutableStateOf(startState) }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(UiKitColors.colors.surface.primary),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        innerState.listFilters.forEachIndexed { index, filterData ->
            val backgroundSurface = when {
                filterData.isChecked -> UiKitColors.colors.primary.`600`
                filterData.isEnabled -> Color.Transparent
                else -> UiKitColors.colors.primary.`300`
            }
            Surface(
                color = backgroundSurface,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(quarterPadding)
                    .height(40.dp)
            ) {
                Box(modifier = Modifier.clickable(
                    enabled = filterData.isEnabled,
                    onClick = {
                        innerState = UiKitRowTabState(
                            innerState.listFilters.mapIndexed { indexTab, tab ->
                                when {
                                    indexTab == index -> tab.copy(isChecked = true)
                                    isMultiplyCheckedEnabled -> tab
                                    else -> tab.copy(isChecked = false)
                                }
                            }
                        )
                        onCheckChanged(!filterData.isChecked, index)
                    }
                )) {
                    Text(
                        text = filterData.text,
                        style = UiKitTypography.TextMD.Regular,
                        color = UiKitColors.colors.type.high,
                        modifier = Modifier.padding(horizontal = padding, vertical = halfPadding)
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun UiKitRowTabLightPreview() {
    UiKitRowTab(
        startState = UiKitRowTabState(
            listOf(
                UiKitTabInfo("Всё", true, true),
                UiKitTabInfo("Доходы", false, true),
                UiKitTabInfo("Расходы", false, false)
            )
        ),
        onCheckChanged = { _, _ -> }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UiKitRowTabDarkPreview() {
    UiKitRowTab(
        startState = UiKitRowTabState(
            listOf(
                UiKitTabInfo("Всё", true, true),
                UiKitTabInfo("Доходы", false, true),
                UiKitTabInfo("Расходы", false, false)
            )
        ),
        onCheckChanged = { _, _ -> }
    )
}