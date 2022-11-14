package ru.bysoft.budget.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.bysoft.budget.auth.data.network.IAuthApi
import ru.bysoft.budget.common.network.MAIN_BASE_URL_NAME
import ru.bysoft.budget.common.network.RetrofitClient
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthNetworkDi {
    companion object {
        @Provides
        fun provideAuthApi(
            @Named(MAIN_BASE_URL_NAME) baseUrl: String
        ): IAuthApi =
            RetrofitClient.getClient(baseUrl).create(IAuthApi::class.java)

        @Provides
        @Named(MAIN_BASE_URL_NAME)
        fun provideBaseUrl(): String = "http://bysoft.ru/"

    }
}