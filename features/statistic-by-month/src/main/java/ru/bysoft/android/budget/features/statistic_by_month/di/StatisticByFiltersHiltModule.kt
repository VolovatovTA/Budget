package ru.bysoft.android.budget.features.statistic_by_month.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.bysoft.android.budget.features.statistic_by_month.data.IStatisticByFiltersRepo
import ru.bysoft.android.budget.features.statistic_by_month.data.StatisticByFiltersRepo

@Module
@InstallIn(ViewModelComponent::class)
interface StatisticByFiltersHiltModule {
    @Binds
    fun bindRepo(impl: StatisticByFiltersRepo): IStatisticByFiltersRepo
}