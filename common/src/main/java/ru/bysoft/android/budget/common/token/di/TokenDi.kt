package ru.bysoft.android.budget.common.token.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.android.budget.common.token.ITokenStorage
import ru.bysoft.android.budget.common.token.TokenStorage

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenDi {

    @Binds
    abstract fun bindTokenRepo(repo: TokenStorage): ITokenStorage
}