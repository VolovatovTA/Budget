package ru.bysoft.android.budget.common.token.di

import org.koin.dsl.module
import ru.bysoft.android.budget.common.token.ITokenStorage
import ru.bysoft.android.budget.common.token.TokenStorage

val TokenDi = module {
    single<ITokenStorage> {
        TokenStorage(get())
    }
}