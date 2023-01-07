package ru.bysoft.budget.features.bottom_navigation.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.bysoft.budget.common.errors.IErrorLogger
import ru.bysoft.budget.features.bottom_navigation.statistic.data.IStatisticRepo
import ru.bysoft.budget.features.bottom_navigation.statistic.navigation.IStatisticNavigation
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity.IStatisticState
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity.StatisticErrorState
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity.StatisticWaitingState
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.mapper.StatisticPresentationMapper
import javax.inject.Inject

interface IStatisticViewModel {
    val state: StateFlow<IStatisticState>
    fun loadData(isRefresh: Boolean)
    fun addCategory()
    fun updateCategory(id: String)
}

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val errorLogger: IErrorLogger,
    private val repo: IStatisticRepo,
    private val mapper: StatisticPresentationMapper,
    private val navigate: IStatisticNavigation
) : ViewModel(), IStatisticViewModel {

    override val state: MutableStateFlow<IStatisticState> = MutableStateFlow(StatisticWaitingState(false))

    private val handler = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        state.value = StatisticErrorState
    }

    override fun loadData(isRefresh: Boolean) {
        viewModelScope.launch(handler) {
            state.value = StatisticWaitingState(isRefresh)
            val data = repo.getCategories()
            state.value = mapper.getState(data)
        }
    }

    override fun addCategory() {
        navigate.toCreateCategory()
    }

    override fun updateCategory(id: String) {
        navigate.toUpdateCategory(id)
    }
}