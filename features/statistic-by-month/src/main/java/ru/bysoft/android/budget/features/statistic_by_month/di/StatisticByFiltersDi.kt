package ru.bysoft.android.budget.features.statistic_by_month.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.features.statistic_by_month.data.IStatisticByFiltersRepo
import ru.bysoft.android.budget.features.statistic_by_month.data.StatisticByFiltersRepo
import ru.bysoft.android.budget.features.statistic_by_month.presentation.StatisticByFiltersViewModel

val StatisticByFiltersDi = module {
    singleOf(::StatisticByFiltersRepo) bind IStatisticByFiltersRepo::class
    viewModelOf(::StatisticByFiltersViewModel)
}