package ru.bysoft.android.budget.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.bysoft.android.budget.auth.data.AuthRepository
import ru.bysoft.android.budget.auth.data.IAuthRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthDi {

    @Binds
    abstract fun bindRepo(repo: AuthRepository): IAuthRepository

}