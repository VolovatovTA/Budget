package ru.bysoft.budget.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.bysoft.budget.auth.data.AuthRepository
import ru.bysoft.budget.auth.data.IAuthRepository
import ru.bysoft.budget.auth.data.mapper.AuthDataMapper
import ru.bysoft.budget.auth.data.mapper.IAuthDataMapper

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthDi {

    @Binds
    abstract fun bindRepo(repo: AuthRepository): IAuthRepository

    @Binds
    abstract fun bindMapper(mapper: AuthDataMapper): IAuthDataMapper
}