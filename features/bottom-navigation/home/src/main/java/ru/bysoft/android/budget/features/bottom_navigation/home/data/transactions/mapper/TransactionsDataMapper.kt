package ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.mapper

import ru.bysoft.android.budget.common.util.dateFormat
import ru.bysoft.android.budget.common.util.getCurrency
import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.entity.*
import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.network.entity.ListTransactionsResponse
import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.network.entity.TransactionResponse
import java.text.SimpleDateFormat
import java.util.Locale

class UnknownTypeTransaction(type: String) :
    Throwable("Allowed EXPENSE, INCOME, TRANSFER, but came: $type")

class UnknownCurrencyException(currency: String) : Throwable(currency)

fun mapToData(response: ListTransactionsResponse, locale: Locale) =
    ListTransactionsData(listTransactions = response.data.map { mapToData(locale, it) })

private fun mapToData(locale: Locale, response: TransactionResponse) = when (response.type) {
    "EXPENSE" -> getExpenseTransaction(response, locale = locale)
    "INCOME" -> getIncomeTransaction(response, locale = locale)
    "TRANSFER" -> getTransferTransaction(response, locale = locale)
    else -> throw UnknownTypeTransaction(response.type)
}

private fun getExpenseTransaction(transactionResponse: TransactionResponse, locale: Locale): TransactionExpense =
    TransactionExpense(
        amount = transactionResponse.amount,
        categories = getCategories(transactionResponse),
        comment = transactionResponse.comment,
        currency = getCurrency(transactionResponse.currency)
            ?: throw UnknownCurrencyException(transactionResponse.currency),
        date = SimpleDateFormat(dateFormat, locale).parse(transactionResponse.createdAt),
        id = transactionResponse.id
    )

private fun getIncomeTransaction(transactionResponse: TransactionResponse, locale: Locale): TransactionIncome =
    TransactionIncome(
        amount = transactionResponse.amount,
        categories = getCategories(transactionResponse),
        comment = transactionResponse.comment,
        currency = getCurrency(transactionResponse.currency)
            ?: throw UnknownCurrencyException(transactionResponse.currency),
        date = SimpleDateFormat(dateFormat, locale).parse(transactionResponse.createdAt),
        id = transactionResponse.id
    )

private fun getTransferTransaction(transactionResponse: TransactionResponse, locale: Locale): TransactionTransfer =
    TransactionTransfer(
        amount = transactionResponse.amount,
        comment = transactionResponse.comment,
        currency = getCurrency(transactionResponse.currency)
            ?: throw UnknownCurrencyException(transactionResponse.currency),
        date = SimpleDateFormat(dateFormat, locale).parse(transactionResponse.createdAt),
        id = transactionResponse.id
    )

private fun getCategories(transactionResponse: TransactionResponse): List<CategoryData> =
    when {
        !transactionResponse.listExpenseCategoryResponse.isNullOrEmpty() -> transactionResponse.listExpenseCategoryResponse.map {
            ExpenseCategory(
                currency = getCurrency(it.currency) ?: throw UnknownCurrencyException(it.currency),
                id = it.id,
                name = it.name,
                iconName = it.iconName,
            )
        }
        transactionResponse.income != null -> listOf(
            IncomeCategory(
                currency = getCurrency(transactionResponse.income.currency)
                    ?: throw UnknownCurrencyException(transactionResponse.income.currency),
                id = transactionResponse.income.id,
                name = transactionResponse.income.name,
                iconName = transactionResponse.income.iconName
            )
        )
        else -> emptyList()
    }