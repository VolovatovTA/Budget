package ru.bysoft.android.budget.uikit.components.listItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
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
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfo
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoError
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoSuccess
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoWaiting
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.icons.another.Wallet
import ru.bysoft.android.budget.uikit.icons.pack.Recycle
import ru.bysoft.android.budget.uikit.styles.UiKitStyles

@Composable
fun UiKitListItem(
    title: String,
    icons: List<ImageVector>,
    amount: UiKitAmountInfo,
    modifier: Modifier = Modifier,
    subTitle: String? = null,
    amountColor: Color,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UiKitListItemIcons(icons)
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

const val count = 3

@Composable
private fun UiKitListItemIcons(icons: List<ImageVector>) {
    var countShowableIcons = icons.size.coerceAtMost(count)
    if (countShowableIcons == 0) countShowableIcons = 1
    val step = 15.dp
    Box(
        modifier = Modifier
            .height(40.dp)
            .padding(start = 30.dp * (count - countShowableIcons) / (count - 1))
    ) {
        (0 until countShowableIcons).forEach { index ->
            Surface(
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp,
                modifier = Modifier
                    .padding(start = step * index)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(UiKitColors.colors.col3),
                    contentAlignment = Alignment.Center
                ) {
                    icons.getOrNull(index)?.let {
                        Icon(
                            it,
                            contentDescription = null,
                            tint = UiKitColors.colors.dark
                        )
                    }
                }
            }
        }
        if (icons.size > count) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp,
                modifier = Modifier
                    .padding(start = step * (count + 1))
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp, 40.dp)
                        .background(UiKitColors.colors.col3),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+${icons.size - count}",
                        style = UiKitStyles.Body2,
                        color = UiKitColors.colors.dark
                    )
                }
            }
        }
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
            icons = listOf(),
            amount = UiKitAmountInfoWaiting,
            amountColor = UiKitColors.colors.dark
        )
        UiKitListItem(
            title = "Title",
            icons = listOf(Wallet),
            amount = UiKitAmountInfoSuccess("1000"),
            amountColor = UiKitColors.colors.dark
        )
        UiKitListItem(
            title = "Title",
            icons = listOf(Wallet, Recycle),
            amount = UiKitAmountInfoSuccess("1000"),
            amountColor = UiKitColors.colors.dark
        )
        UiKitListItem(
            title = "Title",
            icons = listOf(Wallet, Recycle, Recycle),
            amount = UiKitAmountInfoSuccess("1000"),
            amountColor = UiKitColors.colors.dark
        )
        UiKitListItem(
            title = "Title",
            icons = listOf(Wallet, Wallet, Wallet, Wallet),
            amount = UiKitAmountInfoError,
            amountColor = UiKitColors.colors.dark
        )
    }

}
