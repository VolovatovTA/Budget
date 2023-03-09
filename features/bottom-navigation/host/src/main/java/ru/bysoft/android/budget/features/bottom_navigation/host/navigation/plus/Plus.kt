package ru.bysoft.android.budget.features.bottom_navigation.host.navigation.plus

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigationButtonInfo
import ru.bysoft.android.budget.common.navigation.NavigationInfo
import ru.bysoft.android.budget.common.util.TransactionTypeEnum
import ru.bysoft.android.budget.common.util.toJson
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.TransactionsCreateNavParams
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.transaction.Transaction
import ru.bysoft.android.budget.uikit.icons.pack.ArrowUp
import ru.bysoft.android.budget.uikit.icons.pack.Minus
import ru.bysoft.android.budget.uikit.icons.pack.Recycle
import ru.bysoft.android.budget.features.bottom_navigation.home.R

object Plus : BottomNavigationButtonInfo {
    override val icon: ImageVector = ArrowUp
    override val label: Int? = null

    @Composable
    override fun backgroundColor() = ru.bysoft.android.budget.uikit.colors.UiKitColors.colors.col4
    val entireList: List<BottomNavigationButtonInfo> = listOf(
        EntirePlus,
        EntireTransfer,
        EntireMinus,
    )
}

object EntirePlus : NavigationInfo(
    Transaction.createScreen,
    "${Transaction.createScreen}/" + TransactionsCreateNavParams(TransactionTypeEnum.INCOME).toJson()
), BottomNavigationButtonInfo {
    override val icon: ImageVector =  ru.bysoft.android.budget.uikit.icons.pack.Plus
    override val label: Int = R.string.filter_income

    @Composable
    override fun backgroundColor() = ru.bysoft.android.budget.uikit.colors.UiKitColors.colors.col4
}

object EntireMinus : NavigationInfo(
    Transaction.createScreen,
    "${Transaction.createScreen}/" + TransactionsCreateNavParams(TransactionTypeEnum.EXPENSE).toJson()
), BottomNavigationButtonInfo {
    override val icon: ImageVector = Minus
    override val label: Int = R.string.filter_expense

    @Composable
    override fun backgroundColor() = ru.bysoft.android.budget.uikit.colors.UiKitColors.colors.col4
}

object EntireTransfer : NavigationInfo(
    Transaction.createScreen,
    "${Transaction.createScreen}/" + TransactionsCreateNavParams(TransactionTypeEnum.TRANSFER).toJson()
), BottomNavigationButtonInfo {
    override val icon: ImageVector = Recycle
    override val label: Int = R.string.filter_transfer

    @Composable
    override fun backgroundColor() = ru.bysoft.android.budget.uikit.colors.UiKitColors.colors.col4
}
