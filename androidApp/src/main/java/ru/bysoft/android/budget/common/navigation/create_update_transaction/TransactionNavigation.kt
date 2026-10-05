package ru.bysoft.android.budget.common.navigation.create_update_transaction

import androidx.navigation.NavHostController
import ru.bysoft.android.budget.common.navigation.NavHostControllerWrapper
import ru.bysoft.android.budget.common.navigation.create_update_categiry.CreateUpdateCategory
import ru.bysoft.android.budget.common.util.CategoryTypeEnum
import ru.bysoft.android.budget.common.util.toJson
import ru.bysoft.android.budget.features.create_udate_category.navigation.CreateCategoryNavInfo
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.ITransactionNavigation

class TransactionNavigation(
    private val navHostController: NavHostControllerWrapper
) : ITransactionNavigation {

    override fun back() {
        navHostController.popBackStack()
    }

    override fun toCreateCategoryExpense() {
        navHostController.navigate(
            "${CreateUpdateCategory.createScreenName}/${
                CreateCategoryNavInfo(
                    CategoryTypeEnum.EXPENSE
                ).toJson()
            }"
        )
    }

    override fun toCreateCategoryIncome() {
        navHostController.navigate(
            "${CreateUpdateCategory.createScreenName}/${
                CreateCategoryNavInfo(
                    CategoryTypeEnum.INCOME
                ).toJson()
            }"
        )
    }
}