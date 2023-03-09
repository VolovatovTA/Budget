package ru.bysoft.android.budget.features.bottom_navigation.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.common.util.getBeautifulAmount
import ru.bysoft.android.budget.common.util.getCalculatedDate
import ru.bysoft.android.budget.features.bottom_navigation.statistic.data.IStatisticRepo
import ru.bysoft.android.budget.features.bottom_navigation.statistic.data.entity.CategoryData
import ru.bysoft.android.budget.features.bottom_navigation.statistic.navigation.IStatisticNavigation
import ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.entity.*
import ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.mapper.StatisticPresentationMapper
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoError
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoSuccess
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoWaiting
import java.util.*
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
    private val navigate: IStatisticNavigation,
    private val locale: Locale
) : ViewModel(), IStatisticViewModel {

    override val state: MutableStateFlow<IStatisticState> =
        MutableStateFlow(StatisticWaitingState(false))

    private val handler = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        state.value = StatisticErrorState
    }

    private val handlerTransactions = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        state.update { statisticState ->
            when (statisticState) {
                is StatisticSuccessState -> {
                    statisticState.copy(
                        listInfo = statisticState.listInfo.map { categoryData ->
                            if (categoryData.amount is UiKitAmountInfoWaiting) {
                                categoryData.copy(
                                    amount = UiKitAmountInfoError
                                )
                            } else {
                                categoryData
                            }
                        }
                    )
                }
                else -> statisticState
            }
        }
    }

    override fun loadData(isRefresh: Boolean) {
        viewModelScope.launch(handler) {
            state.value = StatisticWaitingState(isRefresh)
            val data = withContext(Dispatchers.IO) {
                val expenses = repo.getCategories()
                withContext(Dispatchers.Main) { state.value = mapper.getState(expenses) }
                expenses
            }

            data.listCategoryData.forEach { categoryData ->
                try {
                    val daysBeforeCurrent = Calendar.getInstance(locale).time.date
                    launch(handlerTransactions) {
                        val dateFrom = getCalculatedDate(locale, -daysBeforeCurrent)
                        val dateTo = getCalculatedDate(locale, 1)
                        val filledCategory = repo.getUpdatedCategoryData(
                            categoryData.id,
                            dateFrom = dateFrom,
                            dateTo = dateTo,
                            oldCategoryData = categoryData
                        )
                        state.update { statisticState ->
                            updateStateByNewCategory(statisticState, filledCategory)
                        }
                    }
                } catch (t: Throwable) {
                    errorLogger.logError(t)
                    state.update { statisticState ->
                        updateStateByNewCategory(statisticState, categoryData.copy(amount = null))
                    }
                }
            }
        }
    }

    private fun updateStateByNewCategory(
        statisticState: IStatisticState,
        filledCategory: CategoryData
    ) = if (statisticState is StatisticSuccessState) {
        statisticState.copy(
            listInfo = statisticState.listInfo.map { category ->
                if (category.id == filledCategory.id) {
                    val (amount, progress) =
                        if (filledCategory.amount != null) {
                            UiKitAmountInfoSuccess(
                                getBeautifulAmount(
                                    filledCategory.amount,
                                    filledCategory.currency
                                )
                            ) to ProgressInfoSuccess(
                                if (filledCategory.amount == 0f && filledCategory.limitAmount == 0f) 0f else
                                    filledCategory.amount / (filledCategory.limitAmount ?: Float.MAX_VALUE)
                            )
                        } else UiKitAmountInfoError to ProgressInfoError
                    mapper.getCategoryState(
                        filledCategory,
                        amount,
                        progress
                    )
                } else
                    category
            }
        )
    } else statisticState

    override fun addCategory() {
        navigate.toCreateCategory()
    }

    override fun updateCategory(id: String) {
        navigate.toUpdateCategory(id)
    }
}