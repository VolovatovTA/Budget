package ru.bysoft.budget.uikit.components.listItem.entity


sealed interface UiKitAmountInfo

data class UiKitAmountInfoSuccess(
    val amount: String
): UiKitAmountInfo

object UiKitAmountInfoError: UiKitAmountInfo

object UiKitAmountInfoWaiting: UiKitAmountInfo