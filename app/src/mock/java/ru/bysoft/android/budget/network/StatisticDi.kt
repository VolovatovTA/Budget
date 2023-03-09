package ru.bysoft.android.budget.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.android.budget.features.bottom_navigation.statistic.data.network.IStatisticApi
import ru.bysoft.android.budget.features.bottom_navigation.statistic.data.network.StatisticApiMock

@Module
@InstallIn(SingletonComponent::class)
abstract class StatisticDi {
    @Binds
    abstract fun bindStatisticApi(api: StatisticApiMock): IStatisticApi
}