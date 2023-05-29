package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.mapper

import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import ru.bysoft.android.budget.common.data_entity.*
import ru.bysoft.android.budget.common.util.dateFormatOutput
import ru.bysoft.android.budget.common.util.getBeautifulAmount
import ru.bysoft.android.budget.common.util.getCurrency
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.transactions.TransactionInfo
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.IWalletPresentation
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletCardPresentation
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletCreateNewPresentation
import ru.bysoft.android.budget.uikit.icons.UiKitIcons
import ru.bysoft.android.budget.uikit.icons.pack.Recycle
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface IHomePresentationMapper {
    fun mapToState(data: List<WalletData>): List<IWalletPresentation>
    fun mapToInfo(
        data: ListTransactionsData,
        confirmStateChange: (value: DismissValue, data: String) -> Boolean
    ): List<TransactionInfo>
}

class HomePresentationMapper @Inject constructor(
    private val locale: Locale
) : IHomePresentationMapper {

    override fun mapToState(data: List<WalletData>) =
        data.map { mapToState(it) }.plus(WalletCreateNewPresentation)

    private fun mapToState(data: WalletData) = WalletCardPresentation(
        name = data.name,
        balance = data.balance,
        currency = data.currency,
        backgroundColor = "primary.500",
        walletId = data.id
    )
    override fun mapToInfo(
        data: ListTransactionsData,
        confirmStateChange: (value: DismissValue, data: String) -> Boolean
    ): List<TransactionInfo> =
        data.listTransactions.map {
            getTransactionInfo(it, confirmStateChange)
        }

    @OptIn(ExperimentalMaterialApi::class)
    private fun getTransactionInfo(
        transactionData: TransactionData,
        confirmStateChange: (value: DismissValue, id: String) -> Boolean
    ): TransactionInfo =
        TransactionInfo(
            amount = when (transactionData) {
                is TransactionIncome -> "+ "
                is TransactionExpense -> "- "
                is TransactionTransfer -> ""
            } + getBeautifulAmount(transactionData.amount, transactionData.currency),
            currency = transactionData.currency,
            date =
            transactionData.date?.let { SimpleDateFormat(dateFormatOutput, locale).format(it) },
            icons =
            if (transactionData is TransactionTransfer) listOf(Recycle)
            else transactionData.categories.mapNotNull { UiKitIcons.getByName(it.iconName) },
            name = transactionData.comment,
            color = when (transactionData) {
                is TransactionIncome -> "feedbackGreen.500"
                is TransactionExpense -> "feedbackRed.500"
                is TransactionTransfer -> "primary.500"
            },
            id = transactionData.id,
            isWaiting = false,
            dismissState = DismissState(DismissValue.Default, confirmStateChange = {confirmStateChange(it, transactionData.id)})
        )
}


