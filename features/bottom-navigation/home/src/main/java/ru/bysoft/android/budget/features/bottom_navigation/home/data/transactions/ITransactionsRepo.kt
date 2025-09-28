package ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions

import ru.budget.android.api.data.mapper.ITransactionsDataMapper
import ru.budget.android.api.data.source.network.ITransactionsApi
import ru.budget.android.api.data.source.network.entity.transactions.TransferTypeEnum
import ru.bysoft.android.budget.common.data_entity.ListTransactionsData

interface ITransactionsRepo {
    suspend fun getTransactions(
        type: String?,
        walletId: List<String>?,
        transferType: TransferTypeEnum,
    ): ListTransactionsData

    suspend fun deleteTransaction(id: String): Result<Unit>
}

class TransactionRepo (
    private val api: ITransactionsApi,
    private val mapper: ITransactionsDataMapper
) : ITransactionsRepo {

    override suspend fun getTransactions(
        type: String?,
        walletId: List<String>?,
        transferType: TransferTypeEnum
    ): ListTransactionsData {
        return mapper.mapToData(
            api.getTransactions(
                type = type,
                wallet_ids = walletId,
                transferType = transferType.nameToBack
            )
        )
    }

    override suspend fun deleteTransaction(id: String): Result<Unit> {
        return try {
            api.deleteTransaction(id)
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

}