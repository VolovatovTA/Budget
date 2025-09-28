package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper

import ru.bysoft.android.budget.currency.getBeautifulAmount
import ru.bysoft.android.budget.currency.getCurrency
import ru.budget.android.api.data.source.network.entity.wallet.WalletListResponse
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.WalletInfo
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.WalletSuccessState

interface ITransactionWalletPresentationMapper {
    fun toPresentation(response: WalletListResponse): WalletSuccessState
}

class TransactionsWalletPresentationMapper :
    ITransactionWalletPresentationMapper {
    override fun toPresentation(response: WalletListResponse): WalletSuccessState {
        return WalletSuccessState(
            list = response.data.map {
                WalletInfo(
                    name = it.name.toString(),
                    balance = getBeautifulAmount(
                        it.balance?.toFloatOrNull() ?: 0f,
                        getCurrency(it.currency)
                    ),
                    id = it.id ?: throw Throwable("Empty id field!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"),
                    currency = getCurrency(it.currency)
                )
            }
        )
    }
}
