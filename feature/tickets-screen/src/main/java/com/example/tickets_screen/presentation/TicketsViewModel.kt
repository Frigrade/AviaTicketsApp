package com.example.tickets_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tickets_screen.domain.usecase.GetTicketsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TicketsViewModel(
    private val router: TicketsRouter,
    private val getTicketsUseCase: GetTicketsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<TicketsState>(TicketsState.Loading)
    val state = _state.asStateFlow()

    private val loadDataExceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.value = TicketsState.Error
    }

    fun loadData() {
        _state.value = TicketsState.Loading

        viewModelScope.launch(loadDataExceptionHandler) {
            _state.value = TicketsState.Content(getTicketsUseCase())
        }
    }

    fun navigateBack() {
        router.navigateBack()
    }
}