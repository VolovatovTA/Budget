package ru.bysoft.android.budget.features.statistic_by_month.presentation

import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

sealed interface StatisticByFiltersState

object StatisticByFiltersStateLoading : StatisticByFiltersState

data class StatisticByFiltersStateSuccess(
    val multiDataSetChartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer()
) : StatisticByFiltersState

object StatisticByFiltersStateError: StatisticByFiltersState
