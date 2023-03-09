package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity

import ru.bysoft.android.budget.common.util.BudgetCurrency
import ru.bysoft.android.budget.uikit.components.currecyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState

sealed class ITransactionState(
    open val commentState: TextFieldState = TextFieldState(),
    open val amountState: TextFieldState = TextFieldState(),
    open val currencyFieldState: CurrencyFieldState = CurrencyFieldState(selectedCurrency = null),
    open val categoryState: CategoryState?,
    open val toastText: String? = null,
    open val exchangeFieldState: List<ExchangeFieldState> = emptyList(),
    open val isLoading: Boolean = false,
    open val walletFromFieldState: IWalletFieldState?,
    open val walletToFieldState: IWalletFieldState?,

    ) {
    abstract fun copyWithAmount(amountState: TextFieldState): ITransactionState
    abstract fun copyWithComment(commentState: TextFieldState): ITransactionState
    abstract fun copyWithCurrency(currencyFieldState: CurrencyFieldState): ITransactionState
    abstract fun copyWithExchanges(exchangeFieldState: List<ExchangeFieldState>): ITransactionState
    abstract fun copyWithLoading(isLoading: Boolean): ITransactionState
    abstract fun copyWithToast(toastText: String?): ITransactionState
    abstract fun copyWithCategory(categoryState: CategoryState): ITransactionState
    abstract fun copyWithWalletFromState(walletFieldState: IWalletFieldState): ITransactionState
    abstract fun copyWithWalletToState(walletFieldState: IWalletFieldState): ITransactionState

}

data class TransactionExpenseState(
    override val commentState: TextFieldState = TextFieldState(),
    override val amountState: TextFieldState = TextFieldState(),
    override val currencyFieldState: CurrencyFieldState = CurrencyFieldState(selectedCurrency = null),
    override val categoryState: CategoryState = CategoryWaiting,
    override val toastText: String? = null,
    override val exchangeFieldState: List<ExchangeFieldState> = emptyList(),
    override val isLoading: Boolean = false,
    override val walletFromFieldState: IWalletFieldState = WalletWaitingState,
) : ITransactionState(
    commentState = commentState,
    amountState = amountState,
    currencyFieldState = currencyFieldState,
    categoryState = categoryState,
    toastText = toastText,
    exchangeFieldState = exchangeFieldState,
    isLoading = isLoading,
    walletFromFieldState = walletFromFieldState,
    walletToFieldState = null
) {
    override fun copyWithAmount(amountState: TextFieldState): TransactionExpenseState =
        this.copy(amountState = amountState)

    override fun copyWithComment(commentState: TextFieldState): TransactionExpenseState =
        this.copy(commentState = commentState)

    override fun copyWithCurrency(currencyFieldState: CurrencyFieldState): TransactionExpenseState =
        this.copy(currencyFieldState = currencyFieldState)

    override fun copyWithExchanges(exchangeFieldState: List<ExchangeFieldState>): TransactionExpenseState =
        this.copy(exchangeFieldState = exchangeFieldState)

    override fun copyWithLoading(isLoading: Boolean): TransactionExpenseState =
        this.copy(isLoading = isLoading)

    override fun copyWithToast(toastText: String?): TransactionExpenseState =
        this.copy(toastText = toastText)

    override fun copyWithCategory(categoryState: CategoryState): TransactionExpenseState =
        this.copy(categoryState = categoryState)

    override fun copyWithWalletFromState(walletFieldState: IWalletFieldState): ITransactionState =
        this.copy(walletFromFieldState = walletFieldState)

    override fun copyWithWalletToState(walletFieldState: IWalletFieldState): ITransactionState =
        this

}

data class TransactionIncomeState(
    override val commentState: TextFieldState = TextFieldState(),
    override val amountState: TextFieldState = TextFieldState(),
    override val currencyFieldState: CurrencyFieldState = CurrencyFieldState(selectedCurrency = null),
    override val categoryState: CategoryState = CategoryWaiting,
    override val toastText: String? = null,
    override val exchangeFieldState: List<ExchangeFieldState> = emptyList(),
    override val isLoading: Boolean = false,
    override val walletToFieldState: IWalletFieldState = WalletWaitingState,
) : ITransactionState(
    commentState = commentState,
    amountState = amountState,
    currencyFieldState = currencyFieldState,
    categoryState = categoryState,
    toastText = toastText,
    exchangeFieldState = exchangeFieldState,
    isLoading = isLoading,
    walletFromFieldState = null,
    walletToFieldState = walletToFieldState,
) {
    override fun copyWithAmount(amountState: TextFieldState): TransactionIncomeState =
        this.copy(amountState = amountState)

    override fun copyWithComment(commentState: TextFieldState): TransactionIncomeState =
        this.copy(commentState = commentState)

    override fun copyWithCurrency(currencyFieldState: CurrencyFieldState): TransactionIncomeState =
        this.copy(currencyFieldState = currencyFieldState)

    override fun copyWithExchanges(exchangeFieldState: List<ExchangeFieldState>): TransactionIncomeState =
        this.copy(exchangeFieldState = exchangeFieldState)

    override fun copyWithLoading(isLoading: Boolean): TransactionIncomeState =
        this.copy(isLoading = isLoading)

    override fun copyWithToast(toastText: String?): TransactionIncomeState =
        this.copy(toastText = toastText)

    override fun copyWithCategory(categoryState: CategoryState): TransactionIncomeState =
        this.copy(categoryState = categoryState)

    override fun copyWithWalletFromState(walletFieldState: IWalletFieldState): TransactionIncomeState =
       this

    override fun copyWithWalletToState(walletFieldState: IWalletFieldState): ITransactionState =
        this.copy(walletToFieldState = walletFieldState)

}

data class TransactionTransferState(
    override val commentState: TextFieldState = TextFieldState(),
    override val amountState: TextFieldState = TextFieldState(),
    override val currencyFieldState: CurrencyFieldState = CurrencyFieldState(selectedCurrency = null),
    override val categoryState: CategoryState? = null,
    override val toastText: String? = null,
    override val exchangeFieldState: List<ExchangeFieldState> = emptyList(),
    override val isLoading: Boolean = false,
    override val walletToFieldState: IWalletFieldState = WalletWaitingState,
    override val walletFromFieldState: IWalletFieldState = WalletWaitingState,
) : ITransactionState(
    commentState = commentState,
    amountState = amountState,
    currencyFieldState = currencyFieldState,
    categoryState = categoryState,
    toastText = toastText,
    exchangeFieldState = exchangeFieldState,
    isLoading = isLoading,
    walletFromFieldState = walletFromFieldState,
    walletToFieldState = walletToFieldState,
) {
    override fun copyWithAmount(amountState: TextFieldState): TransactionTransferState =
        this.copy(amountState = amountState)

    override fun copyWithComment(commentState: TextFieldState): TransactionTransferState =
        this.copy(commentState = commentState)

    override fun copyWithCurrency(currencyFieldState: CurrencyFieldState): TransactionTransferState =
        this.copy(currencyFieldState = currencyFieldState)

    override fun copyWithExchanges(exchangeFieldState: List<ExchangeFieldState>): TransactionTransferState =
        this.copy(exchangeFieldState = exchangeFieldState)

    override fun copyWithLoading(isLoading: Boolean): TransactionTransferState =
        this.copy(isLoading = isLoading)

    override fun copyWithToast(toastText: String?): TransactionTransferState =
        this.copy(toastText = toastText)

    override fun copyWithCategory(categoryState: CategoryState): TransactionTransferState =
        this

    override fun copyWithWalletFromState(walletFieldState: IWalletFieldState): TransactionTransferState =
        this.copy(walletFromFieldState = walletFieldState)

    override fun copyWithWalletToState(walletFieldState: IWalletFieldState): TransactionTransferState =
        this.copy(walletToFieldState = walletFieldState)
}

sealed interface CategoryState

object CategoryError :
    CategoryState

object CategoryWaiting :
    CategoryState

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

object WalletErrorState :
    IWalletFieldState
object WalletWaitingState :
    IWalletFieldState

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