package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title

sealed interface IMeState

data class MeSuccessState(
    val name: String = "",
) : IMeState

object MeErrorState : IMeState

object MeLoadingState : IMeState