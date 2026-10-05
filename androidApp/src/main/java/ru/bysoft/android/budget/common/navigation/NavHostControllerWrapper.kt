package ru.bysoft.android.budget.common.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class NavHostControllerWrapper {
    private val _controller = MutableStateFlow<NavHostController?>(null)
    val controller = _controller.asStateFlow()

    fun set(controller: NavHostController) {
        _controller.update { controller }
    }

    fun navigate(route: String) {
        controller.value?.navigate(route)
    }

    fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit) {
        controller.value?.navigate(route, builder)
    }

    fun popBackStack() {
        controller.value?.popBackStack()
    }

    suspend fun currentBackStackEntry(): NavBackStackEntry? =
        controller.filterNotNull().first().currentBackStackEntry


}