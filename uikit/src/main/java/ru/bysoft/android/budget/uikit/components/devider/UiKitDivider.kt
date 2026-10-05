package ru.bysoft.android.budget.uikit.components.devider

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.halfPadding

enum class DividerTextPosition {
    LEFT, CENTER, RIGHT
}

@Composable
fun UiKitDivider(
    modifier: Modifier = Modifier,
    text: String? = null,
    position: DividerTextPosition = DividerTextPosition.CENTER,
) {
    val color =
        if (isSystemInDarkTheme()) UiKitColors.colors.neutral.`300`
        else UiKitColors.colors.neutral.`500`
    Box(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .align(Alignment.Center)
                .height(1.dp)
                .background(color)
                .fillMaxWidth()
        )
        text?.let {
            Text(
                text = text,
                style = UiKitTypography.TextSM.Regular,
                color = color,
                modifier = Modifier
                    .align(
                        when (position) {
                            DividerTextPosition.LEFT -> Alignment.CenterStart
                            DividerTextPosition.CENTER -> Alignment.Center
                            DividerTextPosition.RIGHT -> Alignment.CenterEnd
                        }
                    )
                    .background(UiKitColors.colors.surface.primary)
                    .padding(horizontal = halfPadding)
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun DividerPreviewLight() {
    Column(verticalArrangement = Arrangement.spacedBy(halfPadding)) {
        UiKitDivider(text = "or continue with")
        UiKitDivider(text = "or continue with", position = DividerTextPosition.LEFT)
        UiKitDivider(text = "or continue with", position = DividerTextPosition.RIGHT)
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF0E1216
)
@Composable
fun DividerPreviewDark() {
    Column(verticalArrangement = Arrangement.spacedBy(halfPadding)) {
        UiKitDivider(text = "or continue with")
        UiKitDivider(text = "or continue with", position = DividerTextPosition.LEFT)
        UiKitDivider(text = "or continue with", position = DividerTextPosition.RIGHT)
    }
}