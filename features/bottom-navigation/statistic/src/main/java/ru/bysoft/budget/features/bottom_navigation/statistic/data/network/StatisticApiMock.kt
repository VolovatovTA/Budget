package ru.bysoft.budget.features.bottom_navigation.statistic.data.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.bysoft.budget.common.util.getStringFromAsset
import ru.bysoft.budget.common.util.restore
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.entity.CategoryExpenseResponse
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.entity.ListTransactionsResponse
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.entity.StatisticExpenseResponse
import javax.inject.Inject

class StatisticApiMock @Inject constructor(
    @ApplicationContext private val context: Context
) : IStatisticApi {
    override suspend fun getExpenses(): StatisticExpenseResponse =
        StatisticExpenseResponse(
            data = listOf(

            )
        )

    override suspend fun getExpensesTransactions(
        type: String?,
        currency: String?,
        dateFrom: String?,
        dateTo: String?,
        expenseIds: List<String>?
    ): ListTransactionsResponse {
        return context.getStringFromAsset("").restore()
    }
}