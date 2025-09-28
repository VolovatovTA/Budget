package ru.bysoft.android.budget.common.navigation.create_update_categiry

import androidx.navigation.NavHostController
import ru.bysoft.android.budget.common.navigation.NavHostControllerWrapper
import ru.bysoft.android.budget.features.create_udate_category.navigation.ICreateUpdateCategoryNavigation

class CreateUpdateCategoryNavigation(
    private val navHostController: NavHostControllerWrapper
): ICreateUpdateCategoryNavigation {
    override fun back() {
        navHostController.popBackStack()
    }
}