package ru.bysoft.android.budget.features.bottom_navigation.host.navigation.plus

import ru.bysoft.android.budget.common.navigation.NavigationInfo
import ru.bysoft.android.budget.common.util.TransactionTypeEnum
import ru.bysoft.android.budget.common.util.toJson
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.TransactionsCreateNavParams
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.transaction.Transaction

object Plus : NavigationInfo(
    Transaction.createScreen,
    "${Transaction.createScreen}/" + TransactionsCreateNavParams(TransactionTypeEnum.EXPENSE).toJson()
) {
    val iconId: Int = ru.bysoft.android.budget.uikit.R.drawable.plus_02
}
