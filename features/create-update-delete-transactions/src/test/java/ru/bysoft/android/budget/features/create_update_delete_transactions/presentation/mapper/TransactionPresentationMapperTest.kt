package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.entity.NewExchange
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.entity.NewTransaction
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.CategoryPresentation
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.CategorySuccess
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.ExchangeFieldState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.TransactionExpenseState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.TransactionIncomeState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.TransactionTransferState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.WalletInfo
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.WalletSuccessState

class TransactionPresentationMapperTest {

    private val mapper = TransactionPresentationMapper()

    private val usd = CurrencyFieldState(selectedCurrency = BudgetCurrencyEnum.USD, list = listOf(BudgetCurrencyEnum.USD))
    private val wallets = WalletSuccessState(
        list = listOf(WalletInfo("Main", "100 $", "w1", BudgetCurrencyEnum.USD), WalletInfo("Cash", "50 €", "w2", BudgetCurrencyEnum.EUR)),
        selectedWalletId = "w1",
    )
    private fun category(id: String, chosen: Boolean) = CategoryPresentation(iconName = null, name = id, isChosen = chosen, id = id, currency = "$")

    @Test
    fun `expense takes the chosen categories and the from-wallet`() {
        val state = TransactionExpenseState(
            amountState = TextFieldState("86.40"),
            commentState = TextFieldState("groceries"),
            currencyFieldState = usd,
            categoryState = CategorySuccess(listOf(category("food", true), category("fun", false), category("home", true))),
            walletFromFieldState = wallets,
        )

        val expected = NewTransaction.Expense(
            amount = 86.40f, comment = "groceries", currency = BudgetCurrencyEnum.USD,
            exchanges = emptyList(), categoryIds = listOf("food", "home"), walletId = "w1",
        )
        assertEquals(expected, mapper.toNewTransaction(state))
    }

    @Test
    fun `income takes the single chosen category and the to-wallet`() {
        val state = TransactionIncomeState(
            amountState = TextFieldState("1500"),
            currencyFieldState = usd,
            categoryState = CategorySuccess(listOf(category("salary", true))),
            walletToFieldState = wallets.copy(selectedWalletId = "w2"),
        )

        val result = mapper.toNewTransaction(state) as NewTransaction.Income
        assertEquals("salary", result.categoryId)
        assertEquals("w2", result.walletId)
        assertEquals(1500f, result.amount)
    }

    @Test
    fun `transfer takes both wallets`() {
        val state = TransactionTransferState(
            amountState = TextFieldState("20"),
            currencyFieldState = usd,
            walletFromFieldState = wallets,
            walletToFieldState = wallets.copy(selectedWalletId = "w2"),
        )

        val result = mapper.toNewTransaction(state) as NewTransaction.Transfer
        assertEquals("w1", result.walletFromId)
        assertEquals("w2", result.walletToId)
    }

    @Test
    fun `missing amount, currency and selections fall back to zero, UNKNOWN and empty`() {
        val state = TransactionExpenseState(amountState = TextFieldState("abc"))

        val result = mapper.toNewTransaction(state) as NewTransaction.Expense
        assertEquals(0f, result.amount)
        assertEquals(BudgetCurrencyEnum.UNKNOWN, result.currency)
        assertEquals(emptyList<String>(), result.categoryIds)
        assertEquals("", result.walletId)
    }

    @Test
    fun `exchange field holding a rate multiplies the base amount`() {
        val result = exchangesOf(amount = "100", entered = "0.9")
        assertEquals(listOf(NewExchange(90f, BudgetCurrencyEnum.EUR)), result)
    }

    @Test
    fun `reverted exchange field divides by the entered rate`() {
        val result = exchangesOf(amount = "100", entered = "2", isRevert = true)
        assertEquals(50f, result.single().amount, 0.0001f)
    }

    @Test
    fun `full-amount exchange field is the converted amount itself`() {
        val result = exchangesOf(amount = "100", entered = "87.5", isFullAmount = true)
        assertEquals(87.5f, result.single().amount)
    }

    @Test
    fun `unparseable exchange field gives zero`() {
        assertEquals(0f, exchangesOf(amount = "100", entered = "").single().amount)
        assertEquals(0f, exchangesOf(amount = "100", entered = "", isFullAmount = true).single().amount)
    }

    private fun exchangesOf(amount: String, entered: String, isRevert: Boolean = false, isFullAmount: Boolean = false): List<NewExchange> {
        val state = TransactionExpenseState(
            amountState = TextFieldState(amount),
            currencyFieldState = usd,
            exchangeFieldState = listOf(
                ExchangeFieldState(
                    enteredAmount = TextFieldState(entered),
                    baseCurrency = BudgetCurrencyEnum.USD,
                    targetCurrency = BudgetCurrencyEnum.EUR,
                    isRevert = isRevert,
                    isFullAmount = isFullAmount,
                )
            ),
        )
        return mapper.toNewTransaction(state).exchanges
    }
}
