package ru.bysoft.budget.common.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonDI {
    companion object {
        @Provides
        @Named(MOCK_DELAY_NAME)
        fun provideMockDelay(): Long = 1L
    }
}