package ru.bysoft.android.budget.features.statistic_by_month.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import ru.bysoft.android.budget.uikit.components.currencyfield.UiKitPopUp
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.PopupFieldState
import ru.bysoft.android.budget.uikit.theme.rememberChartStyle
import ru.bysoft.android.budget.uikit.theme.rememberMarker
import ru.bysoft.android.budget.features.statistic_by_month.presentation.StatisticByFiltersStateSuccess

@Composable
fun StatisticByFiltersScreenSuccess(
    state: StatisticByFiltersStateSuccess = StatisticByFiltersStateSuccess()
) {
    var selectedCategory by remember { mutableStateOf(0) }
    var selectedPeriod by remember { mutableStateOf("День") }
    Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
        StatisticByFilterChart(state.multiDataSetChartEntryModelProducer)
        Row(horizontalArrangement = Arrangement.spacedBy(30.dp)) {
            UiKitPopUp(
                info = PopupFieldState(selectedValue = selectedCategory, list = listOf(0, 1, 2)),
                onClickItem = { selectedCategory = it },
                modifier = Modifier
                    .height(57.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Категория $it",
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                )
            }
            UiKitPopUp(
                info = PopupFieldState(
                    selectedValue = selectedPeriod,
                    list = listOf("День", "Неделя", "Месяц")
                ),
                onClickItem = { selectedPeriod = it },
                modifier = Modifier
                    .height(57.dp)
                    .weight(1f)
            ) {
                Text(
                    text = it.orEmpty(),
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                )
            }
        }
    }
}

@Preview(
    apiLevel = 30,
)
@Composable
fun Preview() {
    StatisticByFiltersScreenSuccess()
}

@Composable
private fun StatisticByFilterChart(chartEntryModelProducer: ChartEntryModelProducer) {
    ProvideChartStyle(rememberChartStyle(chartColors)) {
        val defaultColumns = currentChartStyle.columnChart.columns
        val bottomTextComponentBuilder = TextComponent.Builder()
        bottomTextComponentBuilder.lineCount = 1
        bottomTextComponentBuilder.textSizeSp = 12f
        Chart(
            chart = columnChart(
                columns = remember(defaultColumns) {
                    defaultColumns.mapIndexed { index, defaultColumn ->
                        val topCornerRadiusPercent =
                            if (index == defaultColumns.lastIndex) DefaultDimens.COLUMN_ROUNDNESS_PERCENT else 0
                        val bottomCornerRadiusPercent =
                            if (index == 0) DefaultDimens.COLUMN_ROUNDNESS_PERCENT else 0
                        LineComponent(
                            defaultColumn.color,
                            defaultColumn.thicknessDp,
                            Shapes.roundedCornerShape(
                                topCornerRadiusPercent,
                                topCornerRadiusPercent,
                                bottomCornerRadiusPercent,
                                bottomCornerRadiusPercent,
                            ),
                        )
                    }
                },
                mergeMode = ColumnChart.MergeMode.Stack,
            ),
            chartModelProducer = chartEntryModelProducer,
            startAxis = startAxis(
                maxLabelCount = START_AXIS_LABEL_COUNT,
                labelRotationDegrees = AXIS_LABEL_ROTATION_DEGREES,
            ),
            bottomAxis = bottomAxis(
                labelRotationDegrees = AXIS_LABEL_ROTATION_DEGREES,
                label = bottomTextComponentBuilder.build(),
                title = "Date, days"
            ),
            marker = rememberMarker(),
        )
    }
}


private const val COLOR_1_CODE = 0xff6438a7
private const val COLOR_2_CODE = 0xff3490de
private const val COLOR_3_CODE = 0xff73e8dc
private const val START_AXIS_LABEL_COUNT = 3
private const val AXIS_LABEL_ROTATION_DEGREES = 0f

private val color1 = Color(COLOR_1_CODE)
private val color2 = Color(COLOR_2_CODE)
private val color3 = Color(COLOR_3_CODE)
private val chartColors = listOf(color1, color2, color3)
