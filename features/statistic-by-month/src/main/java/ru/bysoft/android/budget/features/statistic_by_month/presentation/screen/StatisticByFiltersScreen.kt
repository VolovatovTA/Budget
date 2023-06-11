package ru.bysoft.android.budget.features.statistic_by_month.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.bysoft.android.budget.features.statistic_by_month.presentation.*
import ru.bysoft.android.budget.uikit.colors.UiKitColors

@Composable
fun StatisticByFiltersScreen(viewModel: StatisticByFiltersViewModel) {
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        backgroundColor = UiKitColors.colors.surface.primary,
    ) {
        val state = viewModel.state.collectAsState().value
        StatisticScreenContent(it, state)
    }
}

@Composable
private fun StatisticScreenContent(
    it: PaddingValues = PaddingValues(),
    state: StatisticByFiltersState
) {
    Column(Modifier.padding(it)) {
        when (state) {
            is StatisticByFiltersStateLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is StatisticByFiltersStateSuccess -> {
                StatisticByFiltersScreenSuccess(state)
            }
            is StatisticByFiltersStateError -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Error while loading data")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewChart() {
    StatisticScreenContent(state = StatisticByFiltersStateSuccess())
}

