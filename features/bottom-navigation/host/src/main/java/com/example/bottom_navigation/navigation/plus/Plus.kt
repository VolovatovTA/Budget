package com.example.bottom_navigation.navigation.plus

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bottom_navigation.navigation.BottomNavigationButtonInfo
import ru.bysoft.budget.common.navigation.NavigationInfo
import ru.bysoft.budget.common.util.TransactionTypeEnum
import ru.bysoft.budget.common.util.toJson
import ru.bysoft.budget.create_update_delete_transactions.navigation.TransactionsCreateNavParams
import ru.bysoft.budget.create_update_delete_transactions.navigation.transaction.Transaction
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.icons.pack.ArrowUp
import ru.bysoft.budget.uikit.icons.pack.Minus
import ru.bysoft.budget.uikit.icons.pack.Plus
import ru.bysoft.budget.uikit.icons.pack.Recycle
import ru.bysoft.budget.features.bottom_navigation.home.R

object Plus : BottomNavigationButtonInfo {
    override val icon: ImageVector = ArrowUp
    override val label: Int? = null

    @Composable
    override fun backgroundColor() = UiKitColors.colors.col4
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
    override val icon: ImageVector = Plus
    override val label: Int = R.string.filter_income

    @Composable
    override fun backgroundColor() = UiKitColors.colors.col4
}

object EntireMinus : NavigationInfo(
    Transaction.createScreen,
    "${Transaction.createScreen}/" + TransactionsCreateNavParams(TransactionTypeEnum.EXPENSE).toJson()
), BottomNavigationButtonInfo {
    override val icon: ImageVector = Minus
    override val label: Int = R.string.filter_expense

    @Composable
    override fun backgroundColor() = UiKitColors.colors.col4
}

object EntireTransfer : NavigationInfo(
    Transaction.createScreen,
    "${Transaction.createScreen}/" + TransactionsCreateNavParams(TransactionTypeEnum.TRANSFER).toJson()
), BottomNavigationButtonInfo {
    override val icon: ImageVector = Recycle
    override val label: Int = R.string.filter_transfer

    @Composable
    override fun backgroundColor() = UiKitColors.colors.col4
}
