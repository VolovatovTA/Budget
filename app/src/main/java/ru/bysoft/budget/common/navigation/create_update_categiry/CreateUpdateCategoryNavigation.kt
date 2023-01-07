package ru.bysoft.budget.common.navigation.create_update_categiry

import androidx.navigation.NavHostController
import ru.bysoft.budget.create_udate_category.navigation.ICreateUpdateCategoryNavigation
import javax.inject.Inject

class CreateUpdateCategoryNavigation @Inject constructor(
    private val navHostController: NavHostController
):ICreateUpdateCategoryNavigation {
    override fun back() {
        navHostController.popBackStack()
    }
}