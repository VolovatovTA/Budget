package ru.bysoft.budget.create_udate_category.data

import retrofit2.HttpException
import ru.bysoft.budget.create_udate_category.data.entity.CategoryData
import ru.bysoft.budget.create_udate_category.data.entity.ErrorCategoryCreate
import ru.bysoft.budget.create_udate_category.data.entity.SuccessCategoryCreate
import ru.bysoft.budget.create_udate_category.data.mapper.ICategoryMapper
import ru.bysoft.budget.create_udate_category.data.network.ICategoryApi
import ru.bysoft.budget.create_udate_category.data.network.entity.CategoryRequest
import ru.bysoft.budget.create_udate_category.data.network.entity.CategoryResponse
import javax.inject.Inject

interface ICategoryRepo {
    suspend fun createCategory(categoryRequest: CategoryRequest, name: String): CategoryData
    suspend fun updateCategory(categoryRequest: CategoryRequest, name: String, id: String): CategoryData
    suspend fun getCategoryInfo(id: String, path: String): CategoryResponse
    suspend fun delete(id: String, path: String): CategoryData
}

class CategoryRepo @Inject constructor(
    private val api: ICategoryApi,
    private val mapper: ICategoryMapper
) : ICategoryRepo {

    override suspend fun createCategory(
        categoryRequest: CategoryRequest,
        name: String
    ): CategoryData {
        return try {
            api.createCategory(categoryRequest, name)
            SuccessCategoryCreate
        } catch (e: HttpException) {
            ErrorCategoryCreate(mapper.getCategoryErrorType(e.response()?.errorBody()?.string()))
        }

    }

    override suspend fun updateCategory(categoryRequest: CategoryRequest, name: String, id: String): CategoryData {
        return try {
            api.updateCategory(categoryRequest, name, id)
            SuccessCategoryCreate
        } catch (e: HttpException) {
            ErrorCategoryCreate(mapper.getCategoryErrorType(e.response()?.errorBody()?.string()))
        }
    }

    override suspend fun getCategoryInfo(id: String, path: String): CategoryResponse {
        return api.getCategory(id, path)
    }

    override suspend fun delete(id: String, path: String): CategoryData {
        api.delete(id, path).execute()
        return SuccessCategoryCreate
    }

}