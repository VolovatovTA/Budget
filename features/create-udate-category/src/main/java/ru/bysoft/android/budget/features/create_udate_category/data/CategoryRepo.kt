package ru.bysoft.android.budget.features.create_udate_category.data

import retrofit2.HttpException
import ru.budget.android.api.data.mapper.CategoryDataMapper
import ru.budget.android.api.data.source.network.ICategoryApi
import ru.budget.android.api.data.source.network.entity.category.CategoryItemResponse
import ru.budget.android.api.data.source.network.entity.category.CategoryRequest

interface ICategoryRepo {
    suspend fun createCategory(categoryRequest: CategoryRequest, name: String): Result<Unit>
    suspend fun updateCategory(categoryRequest: CategoryRequest, name: String, id: String): Result<Unit>
    suspend fun getCategoryInfo(id: String, path: String): CategoryItemResponse
    suspend fun delete(id: String, path: String): Result<Unit>
}

class CategoryRepo(
    private val api: ICategoryApi,
    private val mapper: CategoryDataMapper
) : ICategoryRepo {

    override suspend fun createCategory(
        categoryRequest: CategoryRequest,
        name: String
    ): Result<Unit> {
        return try {
            api.createCategory(categoryRequest, name)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(mapper.getCategoryErrorType(e.response()?.errorBody()?.string()))
        }

    }

    override suspend fun updateCategory(
        categoryRequest: CategoryRequest,
        name: String,
        id: String
    ): Result<Unit> {

        return try {
            api.updateCategory(categoryRequest, name, id)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(mapper.getCategoryErrorType(e.response()?.errorBody()?.string()))
        }
    }

    override suspend fun getCategoryInfo(id: String, path: String): CategoryItemResponse {
        return api.getCategory(id, path)
    }

    override suspend fun delete(id: String, path: String): Result<Unit>  {
        api.delete(id, path)
        return Result.success(Unit)
    }

}