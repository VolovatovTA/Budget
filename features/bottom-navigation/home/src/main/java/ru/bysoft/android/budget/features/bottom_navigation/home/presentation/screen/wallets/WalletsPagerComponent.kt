package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.screen.wallets

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import ru.bysoft.android.budget.common.util.getBeautifulAmount
import ru.bysoft.android.budget.common.util.getCurrency
import ru.bysoft.android.budget.features.bottom_navigation.home.R
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.*
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.*
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.android.budget.uikit.icons.pack.Edit
import ru.bysoft.android.budget.uikit.icons.pack.Plus
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import kotlin.math.absoluteValue

val heightWalletCard = 91.dp
val cornersRadius = 15.dp
val padding = 10.dp

@Composable
@OptIn(ExperimentalPagerApi::class)
fun WalletsPagerComponent(
    state: IWalletsState,
    onClickSimple: (String) -> Unit,
    onClickCreate: () -> Unit,
    onClickEdit: (id: String) -> Unit,
) {
    val pagerState = rememberPagerState(0)

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is WalletsLoadingState -> LoadingWallets(pagerState)
            is WalletsErrorState -> ErrorWallets()
            is WalletsSuccessState -> SuccessWallets(
                state,
                pagerState,
                onClickCreate,
                onClickEdit,
                onClickSimple,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
internal fun SuccessWallets(
    state: WalletsSuccessState,
    pagerState: PagerState,
    onClickCreate: () -> Unit,
    onClickEdit: (id: String) -> Unit,
    onPositionSelected: (id: String) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(15.dp))
        WalletsPagerComponent(state, pagerState, onPositionSelected, onClickCreate, onClickEdit)
        Spacer(Modifier.height(10.dp))
        HorizontalPagerIndicator(
            pagerState,
            pageCount = state.list.size,
            activeColor = UiKitColors.colors.neutral.`800`,
            inactiveColor = UiKitColors.colors.neutral.`400`
        )
    }
}

@Composable
internal fun ErrorWallets() {
    Box(
        modifier = Modifier
            .height(heightWalletCard + 28.5.dp)
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
@OptIn(ExperimentalPagerApi::class)
internal fun LoadingWallets(pagerState: PagerState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(padding / 2))
        HorizontalPager(
            count = 2,
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 30.dp, end = heightWalletCard * 2),
            itemSpacing = padding
        ) {
            UiKitShimmerComponent(
                modifier = Modifier
                    .height(heightWalletCard)
                    .fillMaxWidth(),
                cornerRadius = cornersRadius
            )
        }
        Spacer(Modifier.height(padding / 2))
        UiKitShimmerComponent(
            modifier = Modifier
                .height(8.dp)
                .width(70.dp), cornerRadius = cornersRadius / 2
        )
    }
}

@SuppressLint("RestrictedApi")
@Composable
@OptIn(ExperimentalPagerApi::class, ExperimentalSnapperApi::class)
private fun WalletsPagerComponent(
    state: WalletsSuccessState,
    pagerState: PagerState,
    onPositionSelected: (id: String) -> Unit,
    onClickCreate: () -> Unit,
    onClickEdit: (id: String) -> Unit,
) {
    val contentPadding = PaddingValues(start = 30.dp, end = heightWalletCard * 2)
    HorizontalPager(
        count = state.list.size,
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            snapIndex = { _, indexStart, indexFinished ->
                val diff = (indexFinished - indexStart)
                if (diff.absoluteValue <= 3) {
                    indexFinished
                } else {
                    indexStart + 3 * diff / diff.absoluteValue
                }
            },
            endContentPadding = contentPadding.calculateEndPadding(LayoutDirection.Ltr)
        ),
        contentPadding = contentPadding,
        itemSpacing = padding * 2
    ) { page ->
        WalletCardComponent(
            info = state.list[page],
            onPositionSelected,
            onClickCreate,
            onClickEdit,
            state.currentWalletId
        )
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
    Card(
        modifier = Modifier
            .height(heightWalletCard)
            .fillMaxWidth(),
        elevation = 5.dp,
        backgroundColor = UiKitColors.card.primaryBackground,
        shape = RoundedCornerShape(cornersRadius)
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
            Icon(
                imageVector = Plus, contentDescription = null
            )
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun WalletSimpleCard(
    info: WalletCardPresentation,
    onClick: (String) -> Unit,
    onClickEdit: (id: String) -> Unit,
    isSelected: Boolean
) {
    Surface(
        modifier = Modifier
            .height(heightWalletCard)
            .fillMaxWidth(),
        elevation = if (isSelected) 10.dp else 5.dp,
        color = if (isSelected) UiKitColors.card.secondaryBackground else UiKitColors.card.primaryBackground,
        shape = RoundedCornerShape(cornersRadius),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { onClick.invoke(info.walletId) },
            verticalAlignment = Alignment.Top,
        ) {
            Column(
                Modifier
                    .background(Color.Transparent)
                    .fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = padding)
                        .padding(top = padding, end = padding * 3)
                        .fillMaxHeight()
                        .weight(1f),
                    text = info.name,
                    style = UiKitTypography.TextXS.Regular,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = padding)
                        .padding(bottom = padding)
                        .fillMaxHeight()
                        .weight(1f),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = getBeautifulAmount(
                            info.balance,
                            getCurrency(info.currency) ?: throw Throwable("UnknownCurrency")
                        ),
                        style = UiKitTypography.DisplayXS.Regular,
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
                    .padding(padding / 2)
                    .clip(RoundedCornerShape(cornersRadius / 2))
                    .clickable { onClickEdit(info.walletId) }
                    .padding(padding / 2),
                tint = UiKitColors.colors.primary.`1100`
            )
        }
    }
}