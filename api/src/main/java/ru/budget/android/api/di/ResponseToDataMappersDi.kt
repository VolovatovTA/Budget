package ru.budget.android.api.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.budget.android.api.data.mapper.ITransactionsDataMapper
import ru.budget.android.api.data.mapper.IWalletsDataMapper
import ru.budget.android.api.data.mapper.TransactionsDataMapper
import ru.budget.android.api.data.mapper.WalletsDataMapper

val ResponseToDataMappersDi = module {
    singleOf(:: TransactionsDataMapper) bind  ITransactionsDataMapper::class
    singleOf(:: WalletsDataMapper) bind  IWalletsDataMapper::class
}