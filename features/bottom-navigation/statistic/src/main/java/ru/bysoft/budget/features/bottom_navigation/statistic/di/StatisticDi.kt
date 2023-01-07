package ru.bysoft.budget.features.bottom_navigation.statistic.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.budget.features.bottom_navigation.statistic.data.IStatisticRepo
import ru.bysoft.budget.features.bottom_navigation.statistic.data.StatisticRepo

@Module
@InstallIn(SingletonComponent::class)
abstract class StatisticDi {
    @Binds
    abstract fun bindRepo(repo: StatisticRepo): IStatisticRepo
}