package ru.budget.android.api.data.source.mock

import android.content.Context
import kotlinx.coroutines.delay
import ru.budget.android.api.data.source.network.ICategoryApi
import ru.budget.android.api.data.source.network.entity.category.*
import ru.budget.android.api.data.source.network.pathToWallet
import ru.bysoft.android.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.android.budget.common.util.getStringFromAsset
import ru.bysoft.android.budget.common.util.pointJson
import ru.bysoft.android.budget.common.util.restore
import java.util.*

class CategoryApiMock(
    private val context: Context,
    private val delayMock: Long
) : ICategoryApi {

    override suspend fun getCategories(name: String): CategoryResponse {
        delay(delayMock)
        return context.getStringFromAsset("$pathToWallet/$name$pointJson").restore()
    }


    override suspend fun createCategory(
        request: CategoryRequest,
        path: String
    ): CategoryItemResponse =
        CategoryItemResponse(
            currency = request.currency,
            id = UUID.randomUUID().toString(),
            name = request.name,
            iconName = "",
            limitAmount = "",
            limitType = "",
        )

    override suspend fun updateCategory(
        request: CategoryRequest,
        path: String,
        id: String
    ): CategoryItemResponse =
        CategoryItemResponse(
            currency = request.currency,
            id = id,
            name = request.name,
            iconName = "",
            limitAmount = "",
            limitType = "",
        )

    override suspend fun getCategory(
        id: String,
        path: String
    ): CategoryItemResponse =
        CategoryItemResponse(
            currency = "USD",
            id = UUID.randomUUID().toString(),
            name = "Mock category",
            iconName = "",
            limitAmount = "",
            limitType = "",
        )

    override suspend fun delete(id: String, path: String) = Unit

}