package ru.bysoft.budget.create_update_delete_transactions.presentation.entity

import ru.bysoft.budget.common.util.BudgetCurrency
import ru.bysoft.budget.uikit.components.currecyfield.entity.CurrencyFieldState
import ru.bysoft.budget.uikit.components.textfield.TextFieldState

data class TransactionState(
    val commentState: TextFieldState = TextFieldState(),
    val amountState: TextFieldState = TextFieldState(),
    val currencyFieldState: CurrencyFieldState = CurrencyFieldState(selectedCurrency = null),
    val typeState: TransactionTypeEnum = TransactionTypeEnum.EXPENSE,
    val categoryState: CategoryState = CategoryWaiting,
    val walletFieldState: IWalletFieldState = WalletWaitingState,
    val toastText: String? = null,
    val exchangeFieldState: List<ExchangeFieldState> = emptyList(),
    val isLoading: Boolean = false
)

enum class TransactionTypeEnum {
    EXPENSE, INCOME, TRANSFER;
}

sealed interface CategoryState

object CategoryError : CategoryState

object CategoryWaiting : CategoryState

data class CategorySuccess(
    val listCategory: List<CategoryPresentation>
) : CategoryState

data class CategoryPresentation(
    val iconName: String?,
    val name: String,
    val isChosen: Boolean = false,
    val id: String,
    val currency: String
)

sealed interface IWalletFieldState

data class WalletSuccessState(
    val list: List<WalletInfo>,
    val selectedWalletId: String? = null

) : IWalletFieldState

object WalletErrorState : IWalletFieldState
object WalletWaitingState : IWalletFieldState

data class WalletInfo(
    val name: String,
    val balance: String,
    val id: String,
    val currency: BudgetCurrency
)

data class ExchangeFieldState(
    val amount: TextFieldState = TextFieldState(),
    val currencyFieldState: CurrencyFieldState = CurrencyFieldState(selectedCurrency = null)
)