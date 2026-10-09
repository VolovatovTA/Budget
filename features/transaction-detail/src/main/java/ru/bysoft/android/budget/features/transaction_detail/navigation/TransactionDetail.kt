package ru.bysoft.android.budget.features.transaction_detail.navigation

import ru.bysoft.android.budget.common.navigation.NavigationInfo

object TransactionDetail : NavigationInfo("transactionDetail", "transactionDetailScreen") {
    const val idKey = "id"
}

interface ITransactionDetailNavigation {
    fun back()
}
