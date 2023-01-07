package ru.bysoft.budget.features.bottom_navigation.home.data.transactions

import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.entity.ListTransactionsData
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.mapper.mapToData
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network.ITransactionsApi
import javax.inject.Inject

interface ITransactionsRepo {
    suspend fun getTransactions(
        type: String?,
        walletId: String?
    ): ListTransactionsData
}

class TransactionRepo @Inject constructor(
    private val api: ITransactionsApi,
) : ITransactionsRepo {

    override suspend fun getTransactions(
        type: String?,
        walletId: String?
    ): ListTransactionsData {
        return api.getTransactions(
            type = type,
            wallet_ids = walletId
        ).mapToData()
    }

}