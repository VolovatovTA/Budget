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
import androidx.compose.runtime.LaunchedEffect
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
import ru.bysoft.android.budget.uikit.styles.UiKitStyles
import kotlin.math.absoluteValue

val heightWalletCard = 91.dp
val cornersRadius = 15.dp
val padding = 10.dp

@Composable
@OptIn(ExperimentalPagerApi::class)
fun WalletsPagerComponent(
    state: IWalletsState,
    onClickSimple: () -> Unit,
    onClickCreate: () -> Unit,
    onClickEdit: (id: String) -> Unit,
    onPositionChanged: (String) -> Unit,
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
                onClickSimple,
                onClickCreate,
                onClickEdit,
                onPositionChanged
            )
        }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
internal fun SuccessWallets(
    state: WalletsSuccessState,
    pagerState: PagerState,
    onClickSimple: () -> Unit,
    onClickCreate: () -> Unit,
    onClickEdit: (id: String) -> Unit,
    onPositionChanged: (id: String) -> Unit
) {
    LaunchedEffect(pagerState.currentPage) {
        val currentWallet = state.list[pagerState.currentPage]
        if (currentWallet is WalletCardPresentation) {
            onPositionChanged(currentWallet.walletId)
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(15.dp))
        WalletsPagerComponent(state, pagerState, onClickSimple, onClickCreate, onClickEdit)
        Spacer(Modifier.height(10.dp))
        HorizontalPagerIndicator(
            pagerState,
            pageCount = state.list.size,
            activeColor = UiKitColors.colors.col1,
            inactiveColor = UiKitColors.colors.col5
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
            style = UiKitStyles.Body2,
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
    onClickSimple: () -> Unit,
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
        itemSpacing = padding*2
    ) { page ->
        WalletCardComponent(
            info = state.list[page], onClickSimple, onClickCreate, onClickEdit
        )
    }
}

@Composable
fun WalletCardComponent(
    info: IWalletPresentation,
    onClickSimple: () -> Unit,
    onClickCreate: () -> Unit,
    onClickEdit: (id: String) -> Unit
) {
    when (info) {
        is WalletCardPresentation -> WalletSimpleCard(info, onClickSimple, onClickEdit)
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
        backgroundColor = UiKitColors.colors.light,
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
                style = UiKitStyles.Body2,
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
    info: WalletCardPresentation, onClick: () -> Unit, onClickEdit: (id: String) -> Unit
) {
    Surface(
        modifier = Modifier
            .height(heightWalletCard)
            .fillMaxWidth(),
//        elevation = 5.dp,
        color = UiKitColors.colors.col3,
        shape = RoundedCornerShape(cornersRadius),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(UiKitColors.colors.col3)
                .clickable { onClick.invoke() },
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
                        .padding(top = padding, end = padding*3)
                        .fillMaxHeight()
                        .weight(1f),
                    text = info.name,
                    style = UiKitStyles.Caption,
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
                        style = UiKitStyles.H2,
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
                tint = UiKitColors.colors.dark
            )
        }
    }
}