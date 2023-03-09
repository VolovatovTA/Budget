package ru.bysoft.android.budget.common.navigation.create_update_categiry

import androidx.navigation.NavHostController
import ru.bysoft.android.budget.features.create_udate_category.navigation.ICreateUpdateCategoryNavigation
import javax.inject.Inject

class CreateUpdateCategoryNavigation @Inject constructor(
    private val navHostController: NavHostController
): ICreateUpdateCategoryNavigation {
    override fun back() {
        navHostController.popBackStack()
    }
}