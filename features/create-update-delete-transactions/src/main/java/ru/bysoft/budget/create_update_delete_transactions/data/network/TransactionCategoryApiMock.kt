package ru.bysoft.budget.create_update_delete_transactions.data.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import ru.bysoft.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.budget.common.util.getStringFromAsset
import ru.bysoft.budget.common.util.restore
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.responses.TransactionsCategoryResponse
import javax.inject.Inject
import javax.inject.Named

class TransactionCategoryApiMock @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named(MOCK_DELAY_NAME) private val delayMock: Long
) : ITransactionsCategoryApi {

    override suspend fun getCategories(name: String): TransactionsCategoryResponse {
        delay(delayMock)
        return context.getStringFromAsset("").restore()
    }
}