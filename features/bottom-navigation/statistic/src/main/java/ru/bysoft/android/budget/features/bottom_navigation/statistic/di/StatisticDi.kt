package ru.bysoft.android.budget.features.bottom_navigation.statistic.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.features.bottom_navigation.statistic.IStatisticViewModel
import ru.bysoft.android.budget.features.bottom_navigation.statistic.StatisticViewModel
import ru.bysoft.android.budget.features.bottom_navigation.statistic.data.IStatisticRepo
import ru.bysoft.android.budget.features.bottom_navigation.statistic.data.StatisticRepo

val StatisticDi = module{
    singleOf(::StatisticRepo) bind IStatisticRepo::class
    viewModelOf(::StatisticViewModel) bind IStatisticViewModel::class
}