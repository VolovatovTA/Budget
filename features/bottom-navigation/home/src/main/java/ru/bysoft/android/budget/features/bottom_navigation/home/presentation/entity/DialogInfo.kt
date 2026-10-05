package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity

data class DialogInfo<T>(
    val title: Int,
    val message: Int,
    val positiveButtonText: Int,
    val negativeButtonText: Int,
    val data: T
)
