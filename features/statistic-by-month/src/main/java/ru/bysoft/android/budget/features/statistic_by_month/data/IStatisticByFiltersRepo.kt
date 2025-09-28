package ru.bysoft.android.budget.features.statistic_by_month.data

import ru.budget.android.api.data.mapper.ITransactionsDataMapper
import ru.budget.android.api.data.source.network.ITransactionsApi
import ru.budget.android.api.data.source.network.entity.transactions.TransactionResponse
import ru.budget.android.api.data.source.network.entity.transactions.TransferTypeEnum
import ru.bysoft.android.budget.common.data_entity.ListTransactionsData

interface IStatisticByFiltersRepo {
    suspend fun getTransactions(
        type: String?,
        walletId: List<String>?,
        transferType: TransferTypeEnum,
    ): List<ListTransactionsData>
}

class StatisticByFiltersRepo constructor(
    private val api: ITransactionsApi,
    private val mapper: ITransactionsDataMapper
) : IStatisticByFiltersRepo {
    override suspend fun getTransactions(
        type: String?,
        walletId: List<String>?,
        transferType: TransferTypeEnum
    ): List<ListTransactionsData> {

        val response = api.getTransactions(
            type = type,
            wallet_ids = walletId,
            transferType = transferType.nameToBack
        )

        return response.data?.groupBy { it?.listTransactionExpenseResponse }?.values?.map {
            TransactionResponse(it)
        }?.map {
            mapper.mapToData(it)
        }.orEmpty()
    }

}