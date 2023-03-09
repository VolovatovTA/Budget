package ru.bysoft.android.budget.features.bottom_navigation.statistic.data.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.bysoft.android.budget.common.util.getStringFromAsset
import ru.bysoft.android.budget.common.util.restore
import ru.bysoft.android.budget.features.bottom_navigation.statistic.data.network.entity.ListTransactionsResponse
import ru.bysoft.android.budget.features.bottom_navigation.statistic.data.network.entity.StatisticExpenseResponse
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