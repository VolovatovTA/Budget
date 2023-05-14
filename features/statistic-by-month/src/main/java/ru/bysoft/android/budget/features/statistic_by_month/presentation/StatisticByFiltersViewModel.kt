package ru.bysoft.android.budget.features.statistic_by_month.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.entry.FloatEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.budget.android.api.data.source.network.entity.transactions.TransferTypeEnum
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.common.util.dateFormatOutput
import ru.bysoft.android.budget.features.statistic_by_month.data.IStatisticByFiltersRepo
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class StatisticByFiltersViewModel @Inject constructor(
    private val errorLogger: IErrorLogger,
    private val repo: IStatisticByFiltersRepo,
    private val locale: Locale
) : ViewModel() {

    val state: StateFlow<StatisticByFiltersState>
        get() = _state.asStateFlow()

    private val _state: MutableStateFlow<StatisticByFiltersState> =
        MutableStateFlow(StatisticByFiltersStateLoading)

    private val handler = CoroutineExceptionHandler { _, throwable ->
        errorLogger.logError(throwable)
        _state.value = StatisticByFiltersStateError
    }

    init {
        loadAllData()
    }

    private fun loadAllData() {
        viewModelScope.launch(handler) {
            _state.value = StatisticByFiltersStateLoading
            val data = repo.getTransactions(
                type = null,
                walletId = null,
                transferType = TransferTypeEnum.WITH_TRANSFER
            )
            val entries = data
                .map { listTransactionsData ->
                    listTransactionsData.listTransactions.map { transactionData ->
                        FloatEntry(
                            x = transactionData.date?.day?.toFloat() ?: 0f,
                            y = transactionData.amount
                        )
                    }
                }
            _state.value = StatisticByFiltersStateSuccess().apply {
                multiDataSetChartEntryModelProducer.setEntries(
                    entries
                )
            }
        }
    }
}