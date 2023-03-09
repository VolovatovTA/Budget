package ru.bysoft.android.budget.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bysoft.android.budget.features.create_update_wallet.data.network.ICreateWalletApi
import ru.bysoft.android.budget.network.interceptors.AUTH_CLIENT_NAME
import ru.bysoft.android.budget.network.interceptors.MAIN_BASE_URL_NAME
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class CreateWalletNetworkDi {
    companion object {
        @Provides
        fun provideCreateWalletApi(
            @Named(MAIN_BASE_URL_NAME) baseUrl: String,
            @Named(AUTH_CLIENT_NAME) client: OkHttpClient
        ): ICreateWalletApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ICreateWalletApi::class.java)
    }
}