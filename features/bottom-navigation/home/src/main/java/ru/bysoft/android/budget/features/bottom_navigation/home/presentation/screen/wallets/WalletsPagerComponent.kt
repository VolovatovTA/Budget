package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.wallets

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.currency.getBeautifulAmount
import ru.bysoft.android.budget.features.bottom_navigation.home.R
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.IWalletPresentation
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.IWalletsState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletCardPresentation
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletCreateNewPresentation
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletsErrorState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletsLoadingState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletsSuccessState
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.icons.UiKitIcons
import ru.bysoft.android.budget.uikit.icons.pack.Edit
import ru.bysoft.android.budget.uikit.icons.pack.Plus
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.corner
import ru.bysoft.android.budget.uikit.styles.doubleCorner
import ru.bysoft.android.budget.uikit.styles.doublePadding
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding
import ru.bysoft.android.budget.uikit.styles.quarterPadding

val widthCard = 200.dp
val height = 105.dp

@Composable
fun WalletsPagerComponent(
    state: IWalletsState,
    onClickSimple: (String) -> Unit,
    onClickCreate: () -> Unit,
    onClickEdit: (id: String) -> Unit,
) {
    val lazyListState = rememberLazyListState(0)

    Crossfade(targetState = state) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(105.dp),
            contentAlignment = Alignment.Center
        ) {
            when (it) {
                is WalletsLoadingState -> LoadingWallets(lazyListState)
                is WalletsErrorState -> ErrorWallets()
                is WalletsSuccessState -> SuccessWallets(
                    it,
                    lazyListState,
                    onClickCreate,
                    onClickEdit,
                    onClickSimple,
                )
            }
        }
    }

}

@Composable
internal fun SuccessWallets(
    state: WalletsSuccessState,
    lazyListState: LazyListState,
    onClickCreate: () -> Unit,
    onClickEdit: (id: String) -> Unit,
    onPositionSelected: (id: String) -> Unit
) {
    val contentPadding = PaddingValues(
        horizontal = padding
    )
    LazyRow(
        state = lazyListState,
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(padding)
    ) {
        items(
            items = state.list
        ) {
            WalletCardComponent(
                info = it,
                onPositionSelected,
                onClickCreate,
                onClickEdit,
                state.currentWalletId
            )
        }
    }
}

@Composable
internal fun ErrorWallets() {
    Box(
        modifier = Modifier
            .height(height)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.error_while_loading_some_data),
            style = UiKitTypography.TextMD.Regular,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
internal fun LoadingWallets(lazyListState: LazyListState) {
    LazyRow(
        state = lazyListState,
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        contentPadding = PaddingValues(start = padding, end = padding),
        horizontalArrangement = Arrangement.spacedBy(padding)
    ) {
        items(2) {
            UiKitShimmerComponent(
                modifier = Modifier
                    .fillMaxHeight(fraction)
                    .width(widthCard),
                cornerRadius = doubleCorner
            )
        }
    }
}

@Composable
fun WalletCardComponent(
    info: IWalletPresentation,
    onClickSimple: (id: String) -> Unit,
    onClickCreate: () -> Unit,
    onClickEdit: (id: String) -> Unit,
    currentWalletId: String
) {
    when (info) {
        is WalletCardPresentation -> WalletSimpleCard(
            info, onClickSimple,
            onClickEdit,
            currentWalletId == info.walletId
        )
        is WalletCreateNewPresentation -> WalletCardCreateNewWallet(onClickCreate)
    }
}

@Composable
private fun WalletCardCreateNewWallet(onClick: () -> Unit) {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    val borderColor = UiKitColors.colors.type.high
    Card(
        modifier = Modifier
            .width(widthCard)
            .fillMaxHeight(fraction),
        elevation = 5.dp,
        backgroundColor = UiKitColors.card(isSelected = false),
        shape = RoundedCornerShape(doubleCorner)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.Transparent)
                .clickable { onClick.invoke() }
        ) {
            Text(
                modifier = Modifier.padding(),
                text = stringResource(R.string.create_new_wallet),
                style = UiKitTypography.TextMD.Regular,
            )
            Spacer(Modifier.height(halfPadding))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .drawBehind {
                        drawCircle(color = borderColor, style = stroke)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Plus,
                    contentDescription = null,
                    tint = UiKitColors.colors.type.high
                )
            }
        }
    }
}

const val fraction = 0.95f

@Composable
fun WalletSimpleCard(
    info: WalletCardPresentation,
    onClick: (String) -> Unit,
    onClickEdit: (id: String) -> Unit,
    isSelected: Boolean
) {
    Surface(
        modifier = Modifier
            .width(widthCard)
            .fillMaxHeight(fraction),
        elevation = if (isSelected) 10.dp else 5.dp,
        color = UiKitColors.card(isSelected),
        shape = RoundedCornerShape(doubleCorner),
    ) {
        Column(
            Modifier
                .clickable { onClick.invoke(info.walletId) }
                .padding(padding - quarterPadding),
            verticalArrangement = Arrangement.spacedBy(halfPadding),
        ) {
            Row {
                Text(
                    modifier = Modifier.padding(end = doublePadding),
                    text = info.name,
                    style = UiKitTypography.TextSM.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = padding),
                    contentAlignment = Alignment.BottomStart
                ) {

                }
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.BottomStart
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(halfPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UiKitIcons.getByName(info.icon)?.let {
                        Icon(
                            painter = painterResource(id = it),
                            contentDescription = null,
                            tint = UiKitColors.colors.type.high
                        )
                    }
                    Text(
                        text = getBeautifulAmount(
                            info.balance,
                            info.currency
                        ),
                        style = UiKitTypography.TextLG.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                imageVector = Edit,
                contentDescription = null,
                modifier = Modifier
                    .padding(halfPadding)
                    .clip(RoundedCornerShape(corner))
                    .clickable { onClickEdit(info.walletId) }
                    .padding(halfPadding),
                tint = UiKitColors.colors.type.high
            )
        }
    }
}