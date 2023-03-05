package ru.bysoft.budget.uikit.components.listItem

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import ru.bysoft.budget.common.util.BudgetCurrency
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.avatar.UiKitAvatar
import ru.bysoft.budget.uikit.components.listItem.entity.UiKitAmountInfo
import ru.bysoft.budget.uikit.components.listItem.entity.UiKitAmountInfoError
import ru.bysoft.budget.uikit.components.listItem.entity.UiKitAmountInfoSuccess
import ru.bysoft.budget.uikit.components.listItem.entity.UiKitAmountInfoWaiting
import ru.bysoft.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.budget.uikit.icons.UiKitIcons
import ru.bysoft.budget.uikit.icons.another.Wallet
import ru.bysoft.budget.uikit.styles.UiKitStyles

@Composable
fun UiKitListItem(
    title: String,
    icon: ImageVector?,
    amount: UiKitAmountInfo,
    modifier: Modifier = Modifier,
    subTitle: String? = null,
    amountColor: Color,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UiKitAvatar(icon)
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

        UiKitListItemAmount(
            info = amount,
            amountColor = amountColor
        )
    }
}

@Composable
fun UiKitListItemAmount(
    info: UiKitAmountInfo,
    amountColor: Color
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        when (info) {
            is UiKitAmountInfoSuccess -> {
                Text(
                    text = info.amount,
                    style = UiKitStyles.Body2,
                    modifier = Modifier,
                    color = amountColor
                )
            }
            is UiKitAmountInfoError -> {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = UiKitColors.colors.red
                )
            }
            is UiKitAmountInfoWaiting -> {
                UiKitShimmerComponent(
                    Modifier
                        .width(50.dp)
                        .height(16.dp),
                    cornerRadius = 4.dp
                )
            }
        }
    }
}


@Preview
@Composable
fun UiKitListItemPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        UiKitListItem(
            title = "Title",
            icon = Wallet,
            amount = UiKitAmountInfoSuccess("1000"),
            amountColor = UiKitColors.colors.dark
        )
        UiKitListItem(
            title = "Title",
            icon = Wallet,
            amount = UiKitAmountInfoError,
            amountColor = UiKitColors.colors.dark
        )
        UiKitListItem(
            title = "Title",
            icon = Wallet,
            amount = UiKitAmountInfoWaiting,
            amountColor = UiKitColors.colors.dark
        )
    }

}
