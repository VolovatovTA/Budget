package ru.bysoft.android.budget.network.common

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.android.budget.common.network.MOCK_DELAY_NAME
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonMockDI {
    companion object {
        @Provides
        @Named(MOCK_DELAY_NAME)
        fun provideMockDelay(): Long = 1000L
    }
}