package ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.transaction

import ru.bysoft.android.budget.common.navigation.NavigationInfo

object Transaction: NavigationInfo("transaction", "transactionCreateScreen") {
    val createScreen = screenName
    const val updateScreen = "transactionUpdateScreen"
}