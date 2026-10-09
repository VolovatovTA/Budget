package ru.bysoft.android.budget.uikit.templates

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.padding

fun topBar(
    title: Int?,
    onBackClick: () -> Unit,
    onClickDelete: (() -> Unit)? = null,
) = @Composable {
    Surface(
        color = UiKitColors.colors.surface.primary,
        elevation = 3.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = padding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(padding)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painterResource(id = R.drawable.arrow_left),
                    contentDescription = null,
                    tint = UiKitColors.colors.type.high
                )
            }

            title?.let {
                Text(
                    text = stringResource(it),
                    style = UiKitTypography.TextLG.Regular,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }

            onClickDelete?.let {
                IconButton(onClick = it) {
                    Icon(
                        painterResource(id = R.drawable.trash_01),
                        contentDescription = null,
                        tint = UiKitColors.colors.feedbackRed.`500`
                    )
                }
            }
        }
    }
}