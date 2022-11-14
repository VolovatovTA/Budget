package ru.bysoft.budget.auth.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.bysoft.budget.auth.data.AuthRepository
import ru.bysoft.budget.auth.data.IAuthRepository
import ru.bysoft.budget.auth.data.mapper.AuthMapper
import ru.bysoft.budget.auth.data.mapper.IAuthMapper
import ru.bysoft.budget.auth.data.network.IAuthApi
import ru.bysoft.budget.common.network.RetrofitClient

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthDi {

    @Binds
    abstract fun bindRepo(repo: AuthRepository): IAuthRepository

    @Binds
    abstract fun bindMapper(mapper: AuthMapper): IAuthMapper
}