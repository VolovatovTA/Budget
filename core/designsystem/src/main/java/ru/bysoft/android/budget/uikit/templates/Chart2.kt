package ru.bysoft.android.budget.uikit.templates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/*
 * Copyright 2023 by Patryk Goworowski and Patrick Michalik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



@Composable
fun ShowcaseScreen(viewModel: ShowcaseViewModel = viewModel()) {
    val composeShowcaseState = rememberLazyListState()
    Scaffold { paddingValues ->
        LazyColumn(
            state = composeShowcaseState,
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(padding),
        ) {
            chartItems(viewModel)
        }
    }
}

private fun LazyListScope.chartItems(viewModel: ShowcaseViewModel) {
    cardItem { Chart1(viewModel.customStepChartEntryModelProducer) }
    cardItem { Chart2(viewModel.chartEntryModelProducer) }
    cardItem { Chart3(viewModel.chartEntryModelProducer) }
    cardItem { Chart4(viewModel.composedChartEntryModelProducer) }
    cardItem { Chart5(viewModel.multiDataSetChartEntryModelProducer) }
//    cardItem { Chart6(viewModel.multiDataSetChartEntryModelProducer) }
//    cardItem { Chart7(viewModel.multiDataSetChartEntryModelProducer) }
//    cardItem { Chart8(viewModel.composedChartEntryModelProducer) }
}

private fun LazyListScope.cardItem(content: @Composable () -> Unit) {
    item {
        Card(shape = MaterialTheme.shapes.large) {
            Box(Modifier.padding(padding)) {
                content()
            }
        }
    }
}

private val padding = 16.dp
