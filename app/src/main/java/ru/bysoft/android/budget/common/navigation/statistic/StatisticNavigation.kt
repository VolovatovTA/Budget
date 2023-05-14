package ru.bysoft.android.budget.common.navigation.statistic

import androidx.navigation.NavHostController
import ru.bysoft.android.budget.common.navigation.DetailStatistic
import ru.bysoft.android.budget.common.navigation.create_update_categiry.CreateUpdateCategory
import ru.bysoft.android.budget.common.util.CategoryTypeEnum
import ru.bysoft.android.budget.common.util.toJson
import ru.bysoft.android.budget.features.bottom_navigation.statistic.navigation.IStatisticNavigation
import ru.bysoft.android.budget.features.create_udate_category.navigation.CreateCategoryNavInfo
import javax.inject.Inject

class StatisticNavigation @Inject constructor(
    private val navHostController: NavHostController
) : IStatisticNavigation {
    override fun toCreateCategory() {
        navHostController.navigate(
            "${CreateUpdateCategory.createScreenName}/${
                CreateCategoryNavInfo(
                    CategoryTypeEnum.EXPENSE
                ).toJson()
            }"
        )
    }

    override fun toUpdateCategory(id: String) {
        navHostController.navigate("${CreateUpdateCategory.updateDeleteScreenName}/$id")
    }

    override fun toDetailStatistic() {
        navHostController.navigate(DetailStatistic.screenName)
    }
}