package ru.bysoft.android.budget.features.transaction_detail.presentation.mapper

import ru.bysoft.android.budget.common.data_entity.TransactionData
import ru.bysoft.android.budget.common.data_entity.TransactionExpense
import ru.bysoft.android.budget.common.data_entity.TransactionIncome
import ru.bysoft.android.budget.common.data_entity.TransactionTransfer
import ru.bysoft.android.budget.common.util.TransactionTypeEnum
import ru.bysoft.android.budget.common.util.dateFormatOutput
import ru.bysoft.android.budget.currency.getBeautifulAmount
import ru.bysoft.android.budget.uikit.icons.UiKitIcons
import ru.bysoft.android.budget.features.transaction_detail.presentation.entity.CategoryItem
import ru.bysoft.android.budget.features.transaction_detail.presentation.entity.TransactionDetailState
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionDetailPresentationMapper(private val locale: Locale) {

    fun toPresentation(transaction: TransactionData): TransactionDetailState.Success =
        TransactionDetailState.Success(
            typeText = when (transaction) {
                is TransactionExpense -> TransactionTypeEnum.EXPENSE
                is TransactionIncome -> TransactionTypeEnum.INCOME
                is TransactionTransfer -> TransactionTypeEnum.TRANSFER
            }.text,
            amount = getBeautifulAmount(transaction.amount, transaction.currency),
            date = transaction.date?.let { SimpleDateFormat(dateFormatOutput, locale).format(it) },
            comment = transaction.comment?.takeIf { it.isNotBlank() },
            categories = transaction.categories.map {
                CategoryItem(name = it.name, icon = UiKitIcons.getByName(it.iconName))
            },
        )
}
