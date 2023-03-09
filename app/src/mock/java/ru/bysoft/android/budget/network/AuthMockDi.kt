package ru.bysoft.android.budget.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.bysoft.android.budget.auth.data.network.AuthApiMock
import ru.bysoft.android.budget.auth.data.network.IAuthApi

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthMockDi {
    @Binds
    abstract fun provideAuthApi(apiMock: AuthApiMock): IAuthApi
}