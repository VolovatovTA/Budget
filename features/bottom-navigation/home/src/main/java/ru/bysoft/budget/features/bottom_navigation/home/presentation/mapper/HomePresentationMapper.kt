package ru.bysoft.budget.features.bottom_navigation.home.presentation.mapper

import ru.bysoft.budget.common.util.getBeautifulAmount
import ru.bysoft.budget.common.util.getCurrency
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.entity.*
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.entity.WalletData
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.transactions.TransactionInfo
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletCardPresentation
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletCreateNewPresentation
import ru.bysoft.budget.uikit.icons.UiKitIcons
import ru.bysoft.budget.uikit.icons.pack.Recycle

fun List<WalletData>.mapToState() = this.map { it.mapToState() }.plus(WalletCreateNewPresentation)

fun WalletData.mapToState() = WalletCardPresentation(
    name = this.name,
    balance = getBeautifulAmount(
        balance,
        getCurrency(currency) ?: throw Throwable("UnknownCurrency")
    ),
    currency = getCurrency(this.currency)?.displayName ?: "*",
    backgroundColor = "col3",
    walletId = this.id
)

fun ListTransactionsData.mapToInfo(): List<TransactionInfo> =
    this.listTransactions.map {
        getTransactionInfo(it)
    }

private fun getTransactionInfo(transactionData: TransactionData): TransactionInfo =
    TransactionInfo(
        amount = when (transactionData) {
            is TransactionIncome -> "+ "
            is TransactionExpense -> "- "
            is TransactionTransfer -> ""
        } + getBeautifulAmount(transactionData.amount, transactionData.currency),
        currency = transactionData.currency,
        date = transactionData.date?.toString(),
        icon =
        if (transactionData is TransactionTransfer) Recycle
        else UiKitIcons.getByName(transactionData.categories.ifEmpty { null }?.first()?.iconName),
        name = transactionData.comment,
        color = when (transactionData) {
            is TransactionIncome -> "col6"
            is TransactionExpense -> "red"
            is TransactionTransfer -> "col1"
        },
        id = transactionData.id
    )


