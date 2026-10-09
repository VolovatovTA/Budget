package ru.bysoft.android.budget.features.create_update_delete_transactions.data

import ru.budget.android.api.data.mapper.CategoryDataMapper
import ru.budget.android.api.data.source.network.ICategoryApi
import ru.bysoft.android.budget.common.data_entity.CategoryData
import ru.bysoft.android.budget.common.util.CategoryTypeEnum

interface ICategoriesRepo {
    suspend fun getCategories(type: CategoryTypeEnum): List<CategoryData>
}

class CategoriesRepo(
    private val api: ICategoryApi,
    private val mapper: CategoryDataMapper,
) : ICategoriesRepo {
    override suspend fun getCategories(type: CategoryTypeEnum): List<CategoryData> =
        mapper.mapToData(api.getCategories(type.pathToBack)).listCategoryData
}
