package ru.bysoft.budget.create_update_delete_transactions.navigation.transaction

import ru.bysoft.budget.common.navigation.NavigationInfo

object Transaction: NavigationInfo("transaction", "transactionCreateScreen") {
    val createScreen = screenName
    const val updateScreen = "transactionUpdateScreen"
}