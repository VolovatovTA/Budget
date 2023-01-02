package ru.bysoft.budget.features.bottom_navigation.home.data.transactions.mapper

import ru.bysoft.budget.common.util.getCurrency
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.entity.*
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network.entity.ListTransactionsResponse
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network.entity.TransactionResponse
import java.util.Date

class UnknownTypeTransaction(type: String) :
    Throwable("Allowed EXPENSE, INCOME, TRANSFER, but came: $type")

class UnknownCurrencyException(currency: String) : Throwable(currency)
object NoCategoriesOccurred : Throwable()


fun ListTransactionsResponse.mapToData() =
    ListTransactionsData(
        listTransactions = this.data.map { it.mapToData() }
    )

private fun TransactionResponse.mapToData() = when (type) {
    "EXPENSE" -> getExpenseTransaction(this)
    "INCOME" -> getIncomeTransaction(this)
    "TRANSFER" -> getTransferTransaction(this)
    else -> throw UnknownTypeTransaction(type)
}

private fun getExpenseTransaction(transactionResponse: TransactionResponse): TransactionExpense =
    TransactionExpense(
        amount = transactionResponse.amount,
        categories = getCategories(transactionResponse),
        comment = transactionResponse.comment,
        currency = getCurrency(transactionResponse.currency)
            ?: throw UnknownCurrencyException(transactionResponse.currency),
        date = Date(transactionResponse.created),
        id = transactionResponse.id
    )

private fun getIncomeTransaction(transactionResponse: TransactionResponse): TransactionIncome =
    TransactionIncome(
        amount = transactionResponse.amount,
        categories = getCategories(transactionResponse),
        comment = transactionResponse.comment,
        currency = getCurrency(transactionResponse.currency)
            ?: throw UnknownCurrencyException(transactionResponse.currency),
        date = Date(transactionResponse.created),
        id = transactionResponse.id
    )

private fun getTransferTransaction(transactionResponse: TransactionResponse): TransactionIncome =
    TransactionIncome(
        amount = transactionResponse.amount,
        categories = getCategories(transactionResponse),
        comment = transactionResponse.comment,
        currency = getCurrency(transactionResponse.currency)
            ?: throw UnknownCurrencyException(transactionResponse.currency),
        date = Date(transactionResponse.created),
        id = transactionResponse.id
    )

private fun getCategories(transactionResponse: TransactionResponse): List<CategoryData> =
    when {
        transactionResponse.listExpenseCategoryResponse != null -> transactionResponse.listExpenseCategoryResponse.map {
            ExpenseCategory(
                currency = getCurrency(it.currency) ?: throw UnknownCurrencyException(it.currency),
                id = it.id,
                name = it.name
            )
        }
        transactionResponse.listIncomeCategoryResponse != null -> listOf(
            IncomeCategory(
                currency = getCurrency(transactionResponse.listIncomeCategoryResponse.currency)
                    ?: throw UnknownCurrencyException(transactionResponse.listIncomeCategoryResponse.currency),
                id = transactionResponse.listIncomeCategoryResponse.id,
                name = transactionResponse.listIncomeCategoryResponse.name
            )
        )
        else -> throw NoCategoriesOccurred
    }