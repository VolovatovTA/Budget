package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper

import ru.bysoft.android.budget.common.data_entity.WalletData
import ru.bysoft.android.budget.currency.getBeautifulAmount
import ru.bysoft.android.budget.currency.getCurrency
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.WalletInfo
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.WalletSuccessState

interface ITransactionWalletPresentationMapper {
    fun toPresentation(wallets: List<WalletData>): WalletSuccessState
}

class TransactionsWalletPresentationMapper : ITransactionWalletPresentationMapper {
    override fun toPresentation(wallets: List<WalletData>): WalletSuccessState =
        WalletSuccessState(
            list = wallets.map {
                val currency = getCurrency(it.currency)
                WalletInfo(
                    name = it.name,
                    balance = getBeautifulAmount(it.balance, currency),
                    id = it.id,
                    currency = currency,
                )
            }
        )
}
