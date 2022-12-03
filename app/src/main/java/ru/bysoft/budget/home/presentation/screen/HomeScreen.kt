package ru.bysoft.budget.home.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import ru.bysoft.budget.home.IHomeViewModel
import ru.bysoft.budget.home.presentation.entity.*
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.budget.uikit.components.listItem.UiKitListItem
import ru.bysoft.budget.uikit.icons.pack.*
import ru.bysoft.budget.uikit.styles.UiKitStyles
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: IHomeViewModel,
    controller: NavHostController
) {
    LaunchedEffect(Unit) {
        viewModel.init(controller)
    }

    val walletsState = viewModel.walletsState.collectAsState().value
    val meState = viewModel.meState.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(), backgroundColor = UiKitColors.white,
        bottomBar = {}
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(UiKitColors.white)
        ) {
            Column(Modifier.shadow(2.dp)) {

                HomeTitleComponent(meState)

                WalletsPagerComponent(walletsState)

                HomeWalletsToggle()
            }

            SimpleList()

        }
    }
}

@Composable
private fun HomeTitleComponent(
    meState: IMeState
) {
    Box(
        modifier = Modifier.height(50.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        when (meState) {
            is MeLoadingState -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(25.dp)
                        .padding(top = 20.dp)
                        .padding(horizontal = 30.dp)
                )
            }
            is MeSuccessState -> {
                Text(
                    text = "Oh. Hi, ${meState.name}!",
                    style = UiKitStyles.H1,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(horizontal = 30.dp)
                )
            }
            is MeErrorState -> {
                Text(
                    text = "Не удалось загрузить данные о вас...",
                    style = UiKitStyles.Body2,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(horizontal = 30.dp)
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
private fun WalletsPagerComponent(state: IWalletsState) {
    val pagerState = rememberPagerState(0)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is WalletsLoadingState -> {
                CircularProgressIndicator(modifier = Modifier.size(30.dp))
            }
            is WalletsErrorState -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Не удалось загрузить данные...",
                        style = UiKitStyles.Body2,
                        textAlign = TextAlign.Center
                    )
                }

            }
            is WalletsSuccessState -> {
                WalletsPagerComponent(state, pagerState)
                HorizontalPagerIndicator(
                    pagerState,
                    pageCount = state.list.size,
                    activeColor = UiKitColors.col5,
                    inactiveColor = UiKitColors.col3
                )
            }
        }
    }
}

@Composable
private fun HomeWalletsToggle() {
    Row(
        modifier = Modifier
            .padding(vertical = 30.dp)
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
//        IconToggleButton(checked = , onCheckedChange = ) {
//
//        }
        UiKitButton(
            UiKitButtonInfo(
                text = "Расход", type = ButtonType.SMALL
            ),
            Modifier.padding(end = 15.dp)

        )
        UiKitButton(
            UiKitButtonInfo(
                text = "Переводы", type = ButtonType.SMALL
            ),
            Modifier
                .padding(end = 15.dp),
        )
        UiKitButton(
            UiKitButtonInfo(
                text = "Доход",
                type = ButtonType.SMALL,
            ),
        )
    }
}

@SuppressLint("RestrictedApi")
@Composable
@OptIn(ExperimentalPagerApi::class, ExperimentalSnapperApi::class)
private fun WalletsPagerComponent(
    state: WalletsSuccessState,
    pagerState: PagerState
) {
    HorizontalPager(
        count = state.list.size,
        state = pagerState,
        modifier = Modifier
            .height(160.dp)
            .fillMaxWidth(),
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            snapIndex = { info, i1, i2 ->
                i2
            },
            endContentPadding = 0.dp
        )
    ) { page ->
        Card(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
//                .graphicsLayer {
//                    // Calculate the absolute offset for the current page from the
//                    // scroll position. We use the absolute value which allows us to mirror
//                    // any effects for both directions
//                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
//
//                    // We animate the scaleX + scaleY, between 85% and 100%
//                    lerp(
//                        0.85f,
//                        1f,
//                        1f - pageOffset.coerceIn(0f, 1f)
//                    ).also { scale: Float ->
//                        scaleX = scale
//                        scaleY = scale
//                    }
//
//                    // We animate the alpha, between 50% and 100%
//                    alpha = lerp(
//                        0.5f,
//                        1f,
//                        1f - pageOffset.coerceIn(0f, 1f)
//                    )
//                }
//                .aspectRatio(1f),
            ,
            elevation = 5.dp,
            backgroundColor = UiKitColors.col3,
            shape = RoundedCornerShape(20.dp)
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = state.list[page].name,
                    style = UiKitStyles.Body2,
                )
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = state.list[page].balance + " " + state.list[page].currency,
                    style = UiKitStyles.Body2,
                )
            }
        }
    }
}

@Composable
fun WalletCardComponent(state: WalletState) {

}


val list = listOf(
    ArrowLeft,
    Filters,
    Beach,
    Minus,
    Oil,
    Health,
    Rest,
    Food,
    Card,
    CheckMark,
    Education,
    Heart,
    House,
    Edit,
    Recycle,
    Search,
    Statistic,
    Multiply,
    Copy,
    Plus,
    ArrowDown,
    ArrowUp,
    ArrowRight,
    Person,
    Shield,
    Clear,
    Delete,
    Internet,
    Menu,
    Photo,
    Tele,
    Calendar,
)

@Composable
fun SimpleList(modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(list.size) {
            UiKitListItem(
                title = list[it].name,
                subTitle = Date(System.currentTimeMillis()).toLocaleString(),
                icon = list[it],
                modifier = Modifier
                    .clickable { }
                    .padding(vertical = 10.dp, horizontal = 30.dp),
                count = "-1900руб"
            )
        }
    }
}