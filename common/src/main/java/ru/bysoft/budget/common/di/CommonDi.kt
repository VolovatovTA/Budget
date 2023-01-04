package ru.bysoft.budget.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.budget.common.errors.ErrorLogger
import ru.bysoft.budget.common.errors.IErrorLogger
import ru.bysoft.budget.common.me_info.IMeInfo
import ru.bysoft.budget.common.me_info.MeInfo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonDi {
    @Binds
    @Singleton
    abstract fun bindMeInfo(meInfo: MeInfo): IMeInfo

    @Binds
    @Singleton
    abstract fun bindErrorLogger(errorLogger: ErrorLogger): IErrorLogger
}