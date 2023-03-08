package ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.wallets

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import ru.bysoft.budget.features.bottom_navigation.home.R
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.*
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.wallets.*
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.shimmer.UiKitShimmerComponent
import ru.bysoft.budget.uikit.icons.pack.Edit
import ru.bysoft.budget.uikit.icons.pack.Plus
import ru.bysoft.budget.uikit.styles.UiKitStyles
import kotlin.math.absoluteValue

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
        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
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
private fun SuccessWallets(
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
        Spacer(Modifier.height(10.dp))
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
private fun ErrorWallets() {
    Box(
        modifier = Modifier
            .height(178.5.dp)
            .fillMaxWidth(), contentAlignment = Alignment.Center
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
private fun LoadingWallets(pagerState: PagerState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(10.dp))
        HorizontalPager(
            count = 2,
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 30.dp, end = 100.dp),
            itemSpacing = 20.dp
        ) {
            UiKitShimmerComponent(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                cornerRadius = 20.dp
            )
        }
        Spacer(Modifier.height(10.dp))
        UiKitShimmerComponent(
            modifier = Modifier
                .height(8.dp)
                .width(70.dp), cornerRadius = 4.dp
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
    val contentPadding = PaddingValues(start = 30.dp, end = 100.dp)
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
        itemSpacing = 20.dp
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
            .height(150.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        backgroundColor = UiKitColors.colors.light,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.Transparent)
                .clickable { onClick.invoke() }
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
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
            .height(150.dp)
            .fillMaxWidth(),
//        elevation = 5.dp,
        color = UiKitColors.colors.col3,
        shape = RoundedCornerShape(20.dp),
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
                        .padding(20.dp)
                        .fillMaxHeight()
                        .weight(1f),
                    text = info.name,
                    style = UiKitStyles.Body2,
                )
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxHeight()
                        .weight(1f),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        modifier = Modifier,
                        text = info.balance,
                        style = UiKitStyles.H1,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(10.dp),
                    color = Color.Transparent
                ) {
                    Icon(
                        imageVector = Edit,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { onClickEdit(info.walletId) }
                            .padding(10.dp),
                        tint = UiKitColors.colors.dark
                    )
                }
            }
        }
    }
}