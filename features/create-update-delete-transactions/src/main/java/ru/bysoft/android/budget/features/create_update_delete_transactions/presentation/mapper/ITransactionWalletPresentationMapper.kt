package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper

import ru.bysoft.android.budget.common.util.getBeautifulAmount
import ru.bysoft.android.budget.common.util.getCurrency
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.entity.responses.TransactionWalletResponse
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.WalletInfo
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.WalletSuccessState
import javax.inject.Inject

interface ITransactionWalletPresentationMapper {
    fun toPresentation(response: TransactionWalletResponse): WalletSuccessState
}

class TransactionsWalletPresentationMapper @Inject constructor() :
    ITransactionWalletPresentationMapper {
    override fun toPresentation(response: TransactionWalletResponse): WalletSuccessState {
        return WalletSuccessState(
            list = response.data.map {
                WalletInfo(
                    name = it.name.toString(),
                    balance = getBeautifulAmount(
                        it.balance ?: 0f,
                        getCurrency(it.currency)
                            ?: throw Throwable("Unknown currency ${it.currency}")
                    ),
                    id = it.id ?: throw Throwable("Empty id field!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"),
                    currency = getCurrency(it.currency)
                        ?: throw Throwable("Unknown currency ${it.currency}")
                )
            }
        )
    }
}
