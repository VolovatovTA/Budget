package ru.bysoft.budget.network

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bysoft.budget.features.bottom_navigation.home.data.me.network.IHomeMeApi
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network.ITransactionsApi
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network.TransactionApiMock
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.network.IHomeWalletsApi
import ru.bysoft.budget.network.interceptors.AUTH_CLIENT_NAME
import ru.bysoft.budget.network.interceptors.MAIN_BASE_URL_NAME
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeNetworkDi {

    companion object {
        @Provides
        fun provideHomeWalletsApi(
            @Named(MAIN_BASE_URL_NAME) baseUrl: String,
            @Named(AUTH_CLIENT_NAME) client: OkHttpClient
        ): IHomeWalletsApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IHomeWalletsApi::class.java)

        @Provides
        fun provideHomeMeApi(
            @Named(MAIN_BASE_URL_NAME) baseUrl: String,
            @Named(AUTH_CLIENT_NAME) client: OkHttpClient
        ): IHomeMeApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IHomeMeApi::class.java)

        @Provides
        fun provideHomeTransactionsApi(
            @Named(MAIN_BASE_URL_NAME) baseUrl: String,
            @Named(AUTH_CLIENT_NAME) client: OkHttpClient
        ): ITransactionsApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITransactionsApi::class.java)

    }
//    @Binds
//    abstract fun bindHomeTransactionsApi(api: TransactionApiMock): ITransactionsApi

}