package ru.bysoft.budget.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.bysoft.budget.home.data.HomeWalletsRepo
import ru.bysoft.budget.home.data.IHomeWalletsRepo

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeDi {

    @Binds
    abstract fun bindWalletsRepo(repo: HomeWalletsRepo): IHomeWalletsRepo

}