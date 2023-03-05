package ru.bysoft.budget.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.budget.create_udate_category.data.network.CategoryApiMock
import ru.bysoft.budget.create_udate_category.data.network.ICategoryApi

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryDi {
    @Binds
    abstract fun bindCategoryApi(api: CategoryApiMock):ICategoryApi
}