package ru.budget.android.api.data.mapper

import ru.bysoft.android.budget.common.data_entity.*
import ru.bysoft.android.budget.common.util.dateFormat
import ru.bysoft.android.budget.common.util.getCurrency
import ru.budget.android.api.data.source.network.entity.transactions.TransactionResponse
import ru.budget.android.api.data.source.network.entity.transactions.TransactionItemResponse
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface ITransactionsDataMapper {
    fun mapToData(response: TransactionResponse): ListTransactionsData
}

class UnknownTypeTransaction(type: String) :
    Throwable("Allowed EXPENSE, INCOME, TRANSFER, but came: $type")

class UnknownCurrencyException(currency: String) : Throwable(currency)
class TransactionsDataMapper @Inject constructor(
    private val locale: Locale
) : ITransactionsDataMapper {
    override fun mapToData(response: TransactionResponse) =
        ListTransactionsData(listTransactions = response.data.map { mapToData(it) })

    private fun mapToData(response: TransactionItemResponse): TransactionData =
        when (response.type) {
            "EXPENSE" -> getExpenseTransaction(response, locale = locale)
            "INCOME" -> getIncomeTransaction(response, locale = locale)
            "TRANSFER" -> getTransferTransaction(response, locale = locale)
            else -> throw UnknownTypeTransaction(response.type)
        }

    private fun getExpenseTransaction(
        transactionItemResponse: TransactionItemResponse,
        locale: Locale
    ): TransactionExpense =
        TransactionExpense(
            amount = transactionItemResponse.amount.toFloatOrNull() ?: 0f,
            categories = getCategories(transactionItemResponse),
            comment = transactionItemResponse.comment,
            currency = getCurrency(transactionItemResponse.currency)
                ?: throw UnknownCurrencyException(transactionItemResponse.currency),
            date = SimpleDateFormat(dateFormat, locale).parse(transactionItemResponse.createdAt),
            id = transactionItemResponse.id
        )

    private fun getIncomeTransaction(
        transactionItemResponse: TransactionItemResponse,
        locale: Locale
    ): TransactionIncome =
        TransactionIncome(
            amount = transactionItemResponse.amount.toFloatOrNull() ?: 0f,
            categories = getCategories(transactionItemResponse),
            comment = transactionItemResponse.comment,
            currency = getCurrency(transactionItemResponse.currency)
                ?: throw UnknownCurrencyException(transactionItemResponse.currency),
            date = SimpleDateFormat(dateFormat, locale).parse(transactionItemResponse.createdAt),
            id = transactionItemResponse.id
        )

    private fun getTransferTransaction(
        transactionItemResponse: TransactionItemResponse,
        locale: Locale
    ): TransactionTransfer =
        TransactionTransfer(
            amount = transactionItemResponse.amount.toFloatOrNull() ?: 0f,
            comment = transactionItemResponse.comment,
            currency = getCurrency(transactionItemResponse.currency)
                ?: throw UnknownCurrencyException(transactionItemResponse.currency),
            date = SimpleDateFormat(dateFormat, locale).parse(transactionItemResponse.createdAt),
            id = transactionItemResponse.id
        )

    private fun getCategories(transactionItemResponse: TransactionItemResponse): List<CategoryData> =
        when {
            !transactionItemResponse.listTransactionExpenseResponse.isNullOrEmpty() -> transactionItemResponse.listTransactionExpenseResponse.map {
                ExpenseCategory(
                    currency = getCurrency(it.currency)
                        ?: throw UnknownCurrencyException(it.currency),
                    id = it.id,
                    name = it.name,
                    iconName = it.iconName,
                )
            }
            transactionItemResponse.transactionIncomeResponse != null -> listOf(
                IncomeCategory(
                    currency = getCurrency(transactionItemResponse.transactionIncomeResponse.currency)
                        ?: throw UnknownCurrencyException(transactionItemResponse.transactionIncomeResponse.currency),
                    id = transactionItemResponse.transactionIncomeResponse.id,
                    name = transactionItemResponse.transactionIncomeResponse.name,
                    iconName = transactionItemResponse.transactionIncomeResponse.iconName
                )
            )
            else -> emptyList()
        }
}