package ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions

import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.entity.ListTransactionsData
import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.mapper.mapToData
import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.network.ITransactionsApi
import java.util.*
import javax.inject.Inject

interface ITransactionsRepo {
    suspend fun getTransactions(
        type: String?,
        walletId: List<String>?,
        transferType: TransferTypeEnum,
    ): ListTransactionsData

    suspend fun deleteTransaction(id: String): Result<Unit>
}

enum class TransferTypeEnum(val nameToBack: String?) {
    WITH_TRANSFER(null),
    WITHOUT_TRANSFER("WITHOUT"),
    ONLY_TRANSFER("ONLY")
}

class TransactionRepo @Inject constructor(
    private val api: ITransactionsApi,
    private val locale: Locale
) : ITransactionsRepo {

    override suspend fun getTransactions(
        type: String?,
        walletId: List<String>?,
        transferType: TransferTypeEnum
    ): ListTransactionsData {
        return mapToData(
            api.getTransactions(
                type = type,
                wallet_ids = walletId,
                transferType = transferType.nameToBack
            ), locale
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