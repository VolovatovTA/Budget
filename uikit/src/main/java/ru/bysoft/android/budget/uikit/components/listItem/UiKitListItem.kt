package ru.bysoft.android.budget.uikit.components.listItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfo
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoError
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoSuccess
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoWaiting
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding
import ru.bysoft.android.budget.uikit.styles.quarterPadding

@Composable
fun UiKitListItem(
    title: String,
    icons: List<Int>,
    amount: UiKitAmountInfo,
    modifier: Modifier = Modifier,
    subTitle: String? = null,
    amountColor: Color,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        UiKitListItemIcons(icons)
        Column(
            modifier = Modifier
                .padding(start = halfPadding)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(quarterPadding)
        ) {
            Text(
                text = title,
                style = UiKitTypography.TextMD.Medium,
                modifier = Modifier,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1

            )
            subTitle?.let {
                Text(
                    text = subTitle,
                    style = UiKitTypography.TextSM.Regular,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = UiKitColors.colors.type.medium
                )
            }
        }

        val width = if (amount is UiKitAmountInfoSuccess) {
            amount.amount.length * lengthOneLetter
        } else {
            Dp.Unspecified
        }
        Box(modifier = Modifier.width(width)) {
            UiKitListItemAmount(
                info = amount,
                amountColor = amountColor,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

val lengthOneLetter = 9.dp

val sizeIcon = 32.dp

@Composable
private fun UiKitListItemIcons(ides: List<Int>) {
    when (ides.size) {
        0 -> {
            Spacer(modifier = Modifier.width(padding))
        }
        1 -> {
            Icon(ides.first(), padding)

        }
        2 -> {
            Box {
                Icon(ides.first(), halfPadding)
                Icon(ides[1], halfPadding + 20.dp)
            }


        }
        3 -> {
            Box {
                Icon(ides.first(), padding)
                Icon(ides[1], padding - 10.dp, 20.dp)
                Icon(ides[2], padding + 10.dp, 20.dp)
            }
        }
        else -> {
            Box {
                Icon(ides.first(), padding)
                Icon(ides[1], padding - 10.dp, 20.dp)
                Icon(ides[2], padding + 10.dp, 20.dp)

                Surface(
                    shape = RoundedCornerShape(sizeIcon),
                    elevation = 5.dp,
                    modifier = Modifier.padding(start = padding + 25.dp, 3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(sizeIcon / 2)
                            .background(UiKitColors.colors.feedbackGreen.`400`),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+${ides.size - 2}",
                            style = UiKitTypography.TextXS.Regular,
                            color = UiKitColors.colors.type.high
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun Icon(id: Int, start: Dp, top: Dp = 0.dp) {
    Surface(
        shape = RoundedCornerShape(sizeIcon / 2),
        elevation = 5.dp,
        modifier = Modifier
            .padding(start = start, top = top)
    ) {
        Icon(
            painterResource(id = id),
            contentDescription = null,
            tint = UiKitColors.colors.type.high,
            modifier = Modifier
                .size(sizeIcon)
                .background(UiKitColors.colors.surface.secondary)
                .padding(quarterPadding)
        )

    }
}

@Composable
fun UiKitListItemAmount(
    info: UiKitAmountInfo,
    amountColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        when (info) {
            is UiKitAmountInfoSuccess -> {
                Text(
                    text = info.amount,
                    style = UiKitTypography.TextMD.Regular,
                    modifier = Modifier,
                    color = amountColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.End
                )
            }
            is UiKitAmountInfoError -> {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = UiKitColors.colors.feedbackRed.`1100`
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
            title = "Title kblyvliylhblkjhblkjblkjblkhjb",
            icons = listOf(),
            amount = UiKitAmountInfoWaiting,
            amountColor = UiKitColors.colors.primary.`1100`
        )
        UiKitListItem(
            title = "Titlevfdjdkfjnbdfkjbdf",
            subTitle = "ksjdvnskdvvgvgvygvygvinsodvnsodvunso",
            icons = listOf(R.drawable.activity),
            amount = UiKitAmountInfoSuccess("1000 000 000 $"),
            amountColor = UiKitColors.colors.primary.`1100`
        )
        UiKitListItem(
            title = "Title",
            icons = listOf(R.drawable.activity, R.drawable.bag),
            amount = UiKitAmountInfoSuccess("1000"),
            amountColor = UiKitColors.colors.primary.`1100`
        )
        UiKitListItem(
            title = "Title",
            icons = listOf(R.drawable.activity, R.drawable.bag, R.drawable.coins_rotate),
            amount = UiKitAmountInfoSuccess("1000"),
            amountColor = UiKitColors.colors.primary.`1100`
        )
        UiKitListItem(
            title = "Title",
            icons = listOf(R.drawable.activity, R.drawable.bag, R.drawable.coins_rotate, R.drawable.computer),
            amount = UiKitAmountInfoError,
            amountColor = UiKitColors.colors.primary.`1100`
        )
    }

}
