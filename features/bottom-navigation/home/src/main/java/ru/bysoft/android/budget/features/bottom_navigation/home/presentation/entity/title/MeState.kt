package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title

import ru.bysoft.android.budget.common.me_info.entity.MeData

sealed interface IMeState

data class MeSuccessState(
    val meData: MeData?,
) : IMeState

object MeErrorState : IMeState

object MeLoadingState : IMeState