package ru.bysoft.android.budget.features.create_update_delete_transactions.navigation

interface ITransactionNavigation {
    fun back()
    fun toCreateCategoryExpense()
    fun toCreateCategoryIncome()
}