package ru.bysoft.android.budget.features.create_update_delete_transactions.data

import retrofit2.HttpException
import ru.budget.android.api.data.source.network.ITransactionsApi
import ru.budget.android.api.data.source.network.entity.transactions.Exchange
import ru.budget.android.api.data.source.network.entity.transactions.Expense
import ru.budget.android.api.data.source.network.entity.transactions.TransactionExpenseCreateRequest
import ru.budget.android.api.data.source.network.entity.transactions.TransactionIncomeCreateRequest
import ru.budget.android.api.data.source.network.entity.transactions.TransactionTransferCreateRequest
import ru.budget.android.api.data.source.network.entity.transactions.error.TransactionErrorResponse
import ru.bysoft.android.budget.common.util.restore
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.entity.NewExchange
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.entity.NewTransaction
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.entity.TransactionCreateException

interface ITransactionsRepo {
    suspend fun create(transaction: NewTransaction)
}

class TransactionsRepo(
    private val transactionApi: ITransactionsApi,
) : ITransactionsRepo {

    override suspend fun create(transaction: NewTransaction) {
        try {
            when (transaction) {
                is NewTransaction.Expense -> transactionApi.createTransactionExpense(transaction.toRequest())
                is NewTransaction.Income -> transactionApi.createTransactionIncome(transaction.toRequest())
                is NewTransaction.Transfer -> transactionApi.createTransactionTransfer(transaction.toRequest())
            }
        } catch (e: HttpException) {
            throw e.toCreateException()
        }
    }

    private fun HttpException.toCreateException(): TransactionCreateException {
        val body = runCatching { response()?.errorBody()?.string()?.restore<TransactionErrorResponse>() }.getOrNull()
        return TransactionCreateException(
            message = body?.message ?: message(),
            fieldErrors = body?.errors.orEmpty(),
        )
    }

    private fun NewTransaction.Expense.toRequest() = TransactionExpenseCreateRequest(
        amount = amount,
        comment = comment,
        currency = currency.iso4217,
        exchanges = exchanges.toRequest(),
        expenses = categoryIds.map { Expense(it) }.ifEmpty { null },
        walletId = walletId,
    )

    private fun NewTransaction.Income.toRequest() = TransactionIncomeCreateRequest(
        amount = amount,
        comment = comment,
        currency = currency.iso4217,
        exchanges = exchanges.toRequest(),
        income_id = categoryId,
        walletId = walletId,
    )

    private fun NewTransaction.Transfer.toRequest() = TransactionTransferCreateRequest(
        amount = amount,
        comment = comment,
        currency = currency.iso4217,
        exchanges = exchanges.toRequest(),
        expenses = null,
        walletIdFrom = walletFromId,
        walletIdTo = walletToId,
    )

    private fun List<NewExchange>.toRequest() = map { Exchange(it.amount, it.currency.iso4217) }
}
