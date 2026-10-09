package ru.bysoft.android.budget.features.transaction_detail.data

import ru.budget.android.api.data.mapper.ITransactionsDataMapper
import ru.budget.android.api.data.source.network.ITransactionsApi
import ru.bysoft.android.budget.common.data_entity.TransactionData

interface ITransactionDetailRepo {
    /** null when there is no transaction with this id */
    suspend fun getTransaction(id: String): TransactionData?
    suspend fun delete(id: String)
}

class TransactionDetailRepo(
    private val api: ITransactionsApi,
    private val mapper: ITransactionsDataMapper,
) : ITransactionDetailRepo {

    // TODO: the backend has no GET transactions/{id}; until it does, pick the one from the list
    override suspend fun getTransaction(id: String): TransactionData? =
        mapper.mapToData(api.getTransactions()).listTransactions.firstOrNull { it.id == id }

    override suspend fun delete(id: String) = api.deleteTransaction(id)
}
