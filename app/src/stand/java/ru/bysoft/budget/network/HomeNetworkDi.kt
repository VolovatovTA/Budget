package ru.bysoft.budget.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bysoft.budget.home.data.me.network.IHomeMeApi
import ru.bysoft.budget.home.data.wallets.network.IHomeWalletsApi
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

    }

}