package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.bysoft.android.budget.common.util.BudgetCurrency
import ru.bysoft.android.budget.common.util.TransactionTypeEnum
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.TransactionsCreateNavParams
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.CategoryPresentation
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.ITransactionState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.TransactionExpenseState
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.ITransactionCreateViewModel

@Preview(
    showSystemUi = true
)
@Composable
fun TransactionScreenPreview() {
    TransactionScreen(
        object : ITransactionCreateViewModel {
            override fun create() = Unit

            override fun initNavParams(argument: TransactionsCreateNavParams) = Unit

            override val toastState: MutableSharedFlow<Int>
                get() = MutableSharedFlow()

            override val state: StateFlow<ITransactionState> =
                MutableStateFlow(TransactionExpenseState())

            override fun setWalletId(fromId: String?, toId: String?) = Unit

            override fun setAmount(amount: String) = Unit

            override fun setCurrency(currency: BudgetCurrency) = Unit

            override fun setCategoriesIds(categoryPresentation: CategoryPresentation) = Unit

            override fun setComment(comment: String) = Unit

            override fun setTypeTransactions(type: TransactionTypeEnum) = Unit

            override fun onEmptyCategoryClick() = Unit

            override fun setFullAmount(currency: BudgetCurrency, newValue: Boolean) = Unit

            override fun setRevert(currency: BudgetCurrency, newValueIsRevert: Boolean) = Unit

            override fun setExchangeAmount(currency: BudgetCurrency, amount: String) = Unit

            override fun back() = Unit

        }
    )
}