package ru.bysoft.android.budget.features.transaction_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.common.errors.handler
import ru.bysoft.android.budget.features.transaction_detail.data.ITransactionDetailRepo
import ru.bysoft.android.budget.features.transaction_detail.navigation.ITransactionDetailNavigation
import ru.bysoft.android.budget.features.transaction_detail.presentation.entity.TransactionDetailState
import ru.bysoft.android.budget.features.transaction_detail.presentation.mapper.TransactionDetailPresentationMapper

interface ITransactionDetailViewModel {
    val state: StateFlow<TransactionDetailState>
    fun load(id: String)
    fun delete()
    fun back()
}

class TransactionDetailViewModel(
    private val repo: ITransactionDetailRepo,
    private val mapper: TransactionDetailPresentationMapper,
    private val navigate: ITransactionDetailNavigation,
    private val errorLogger: IErrorLogger,
) : ViewModel(), ITransactionDetailViewModel {

    override val state = MutableStateFlow<TransactionDetailState>(TransactionDetailState.Loading)
    private var id: String? = null

    private val loadHandler = errorLogger.handler { state.value = TransactionDetailState.Error }

    // a failed delete keeps the screen; the user can retry or go back
    private val deleteHandler = errorLogger.handler {
        state.update { (it as? TransactionDetailState.Success)?.copy(isDeleting = false) ?: it }
    }

    override fun load(id: String) {
        if (this.id == id) return
        this.id = id
        state.value = TransactionDetailState.Loading
        viewModelScope.launch(loadHandler) {
            state.value = repo.getTransaction(id)
                ?.let(mapper::toPresentation)
                ?: TransactionDetailState.NotFound
        }
    }

    override fun delete() {
        val id = id ?: return
        val current = state.value as? TransactionDetailState.Success ?: return
        if (current.isDeleting) return
        state.value = current.copy(isDeleting = true)
        viewModelScope.launch(deleteHandler) {
            repo.delete(id)
            navigate.back()
        }
    }

    override fun back() = navigate.back()
}
