package ru.bysoft.android.budget.uikit.templates

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.icons.pack.ArrowLeft
import ru.bysoft.android.budget.uikit.icons.pack.Delete
import ru.bysoft.android.budget.uikit.styles.UiKitTypography

@Composable
fun UiKitTopBar(id: Int, onClickBack: () -> Unit, onClickDelete: (() -> Unit)?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Icon(
            imageVector = ArrowLeft,
            contentDescription = null,
            modifier = Modifier.clickable(onClick = onClickBack)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = stringResource(id), style = UiKitTypography.DisplayXS.Regular
        )
        Spacer(modifier = Modifier.width(15.dp))

        onClickDelete?.let {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Delete,
                    contentDescription = null,
                    tint = UiKitColors.colors.feedbackRed.`1100`,
                    modifier = Modifier.clickable(onClick = onClickDelete)
                )
            }
        }

        Spacer(modifier = Modifier.width(15.dp))

    }
}