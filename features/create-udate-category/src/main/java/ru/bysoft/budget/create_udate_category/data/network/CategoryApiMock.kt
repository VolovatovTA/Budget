package ru.bysoft.budget.create_udate_category.data.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bysoft.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.budget.common.util.toJson
import ru.bysoft.budget.create_udate_category.data.network.entity.CategoryRequest
import ru.bysoft.budget.create_udate_category.data.network.entity.CategoryResponse
import java.util.UUID
import javax.inject.Inject
import javax.inject.Named

class CategoryApiMock @Inject constructor(
    @Named(MOCK_DELAY_NAME) private val delay: Long,
    @ApplicationContext private val context: Context,
) : ICategoryApi {

    override suspend fun createCategory(request: CategoryRequest, path: String): CategoryResponse =
        CategoryResponse(
            currency = request.currency,
            id = UUID.randomUUID().toString(),
            name = request.name
        )

    override suspend fun updateCategory(
        request: CategoryRequest,
        path: String,
        id: String
    ): CategoryResponse =
        CategoryResponse(
            currency = request.currency,
            id = id,
            name = request.name
        )

    override suspend fun getCategory(id: String, path: String): CategoryResponse =
        CategoryResponse(
            currency = "RUR",
            id = UUID.randomUUID().toString(),
            name = "Им ямоковской категории"
        )

    override fun delete(id: String, path: String): Call<Unit> = EmptyAnswer
}

object EmptyAnswer : Call<Unit> {
    override fun clone(): Call<Unit> = this

    override fun execute(): Response<Unit> = Response.success(Unit)

    override fun enqueue(callback: Callback<Unit>) {}

    override fun isExecuted(): Boolean = true

    override fun cancel() {}

    override fun isCanceled(): Boolean = false

    override fun request(): Request = Request.Builder().build()

    override fun timeout(): Timeout = Timeout()

}
