package ru.bysoft.android.budget.common.navigation.statistic

import androidx.navigation.NavHostController
import ru.bysoft.android.budget.common.navigation.create_update_categiry.CreateUpdateCategory
import ru.bysoft.android.budget.features.bottom_navigation.statistic.navigation.IStatisticNavigation
import javax.inject.Inject

class StatisticNavigation @Inject constructor(
    private val navHostController: NavHostController
) : IStatisticNavigation {
    override fun toCreateCategory() {
        navHostController.navigate(CreateUpdateCategory.createScreenName)
    }

    override fun toUpdateCategory(id: String) {
        navHostController.navigate("${CreateUpdateCategory.updateDeleteScreenName}/$id")
    }
}