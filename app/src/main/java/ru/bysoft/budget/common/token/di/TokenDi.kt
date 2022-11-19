package ru.bysoft.budget.common.token.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.budget.common.token.ITokenRepo
import ru.bysoft.budget.common.token.TokenRepo

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenDi {

    @Binds
    abstract fun bindTokenRepo(repo: TokenRepo): ITokenRepo
}