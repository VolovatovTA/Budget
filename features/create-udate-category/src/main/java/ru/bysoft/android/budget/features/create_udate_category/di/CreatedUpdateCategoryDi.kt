package ru.bysoft.android.budget.features.create_udate_category.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.android.budget.features.create_udate_category.data.CategoryRepo
import ru.bysoft.android.budget.features.create_udate_category.data.ICategoryRepo

@Module
@InstallIn(SingletonComponent::class)
abstract class CreatedUpdateCategoryDi {

    @Binds
    abstract fun bindRepo(repo: CategoryRepo): ICategoryRepo
}