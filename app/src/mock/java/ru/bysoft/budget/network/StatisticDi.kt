package ru.bysoft.budget.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.IStatisticApi

@Module
@InstallIn(SingletonComponent::class)
abstract class StatisticDi {
    @Binds
    abstract fun bindStatisticApi(api: StatisticApiMock): IStatisticApi
}