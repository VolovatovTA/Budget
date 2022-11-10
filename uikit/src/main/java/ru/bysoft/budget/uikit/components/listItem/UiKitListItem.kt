package ru.bysoft.budget.uikit.components.listItem

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.uikit.components.avatar.UiKitAvatar
import ru.bysoft.budget.uikit.styles.UiKitStyles

@Composable
fun UiKitListItem(
    title: String,
    icon: ImageVector,
    count: String,
    modifier: Modifier = Modifier,
    subTitle: String? = null,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UiKitAvatar(
            icon
        )
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text(
                text = title,
                style = UiKitStyles.Body2,
                modifier = Modifier.padding(bottom = 9.dp)
            )
            subTitle?.let {
                Text(
                    text = subTitle,
                    style = UiKitStyles.Caption,
                    modifier = Modifier.padding()
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = count,
                style = UiKitStyles.Body2,
                modifier = Modifier
            )
        }
    }


}