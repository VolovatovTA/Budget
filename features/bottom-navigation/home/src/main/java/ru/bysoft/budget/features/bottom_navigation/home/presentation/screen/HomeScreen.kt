package ru.bysoft.budget.features.bottom_navigation.home.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import ru.bysoft.budget.features.bottom_navigation.home.IHomeViewModel
import ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.title.HomeTitleComponent
import ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.transactions.HomeTransactionsComponent
import ru.bysoft.budget.features.bottom_navigation.home.presentation.screen.wallets.WalletsPagerComponent
import ru.bysoft.budget.home.presentation.screen.transactions.HomeFiltersComponent
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.icons.pack.*
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: IHomeViewModel,
) {
    LaunchedEffect(Unit) { viewModel.loadData() }

    val walletsState = viewModel.walletsState.collectAsState().value
    val meState = viewModel.meState.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.Transparent,
        bottomBar = {}
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            Column(Modifier.bottomElevation()) {

                HomeTitleComponent(meState)

                WalletsPagerComponent(
                    walletsState,
                    viewModel::onClickSimpleWallet,
                    viewModel::onClickCreateWallet,
                    viewModel::onClickEditWallet
                )

                HomeFiltersComponent()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(UiKitColors.colors.dark40)
                )
            }

            HomeTransactionsComponent()

        }
    }
}

private fun Modifier.bottomElevation(elevation: Dp = 8.dp): Modifier =
    this.then(Modifier.drawWithContent {
        val paddingPx = elevation.toPx()
        clipRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = size.height + paddingPx
        ) {
            this@drawWithContent.drawContent()
        }
    })