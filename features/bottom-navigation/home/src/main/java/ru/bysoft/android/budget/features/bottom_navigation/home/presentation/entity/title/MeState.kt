package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title

import ru.bysoft.android.budget.common.me_info.entity.MeData
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum

sealed interface IMeState

data class MeSuccessState(
    val meData: MeData?,
    val balance: Float = 0f,
    val currency: BudgetCurrencyEnum = BudgetCurrencyEnum.USD
) : IMeState

object MeErrorState : IMeState

object MeLoadingState : IMeState