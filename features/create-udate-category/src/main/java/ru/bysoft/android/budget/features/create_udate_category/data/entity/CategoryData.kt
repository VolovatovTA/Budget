package ru.bysoft.android.budget.features.create_udate_category.data.entity

sealed interface CategoryData

object SuccessCategoryCreate : CategoryData

data class ErrorCategoryCreate(
    val errorType: CategoryErrorType
) : CategoryData

enum class CategoryErrorType(val messageFromBack: String?) {
    INVALID_CURRENCY_("invalid-currency"),
    INVALID_NAME_____("invalid-name"),
    NO_UNIQ_NAME_____("expense-name-musq-be-unique"),
    INVALID_ICON_NAME("invalid-icon-name"),
    UNKNOWN_ERROR(null),
    NULL_ERROR(null),
    TECHNICAL_ERROR_IN_BACK(null);
}