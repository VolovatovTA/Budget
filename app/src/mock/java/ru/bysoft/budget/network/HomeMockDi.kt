package ru.bysoft.budget.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.budget.home.data.network.HomeWalletsApiMock
import ru.bysoft.budget.home.data.network.IHomeWalletsApi


@Module
@InstallIn(SingletonComponent::class)
abstract class HomeMockDi {
    @Binds
    abstract fun bindApiMock(api: HomeWalletsApiMock): IHomeWalletsApi
}