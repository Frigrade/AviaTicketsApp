package com.example.main_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main_screen.domain.usecase.GetDestinationCityFlowUseCase
import com.example.main_screen.domain.usecase.GetOffersUseCase
import com.example.main_screen.domain.usecase.GetTicketsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val router: MainRouter,
    private val getOffersUseCase: GetOffersUseCase,
    private val getTicketsUseCase: GetTicketsUseCase,
    getDestinationCityFlowUseCase: GetDestinationCityFlowUseCase
) : ViewModel() {

    private companion object {
        const val SEARCH_TIMEOUT = 100L
    }

    private val _errorChannel = Channel<Unit>()
    val errorChannel = _errorChannel.receiveAsFlow()

    private val _state = MutableStateFlow<MainState>(MainState.Loading)
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    private val loadDataExceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.value = MainState.InitialLoadError
    }

    private val searchExceptionHandler = CoroutineExceptionHandler { _, _ ->
        viewModelScope.launch {
            _errorChannel.send(Unit)
        }
    }

    init {
        getDestinationCityFlowUseCase().debounce(SEARCH_TIMEOUT).onEach {
            val contentState = _state.value as? MainState.Content ?: return@onEach
            searchJob?.cancel()

            searchJob = viewModelScope.launch(searchExceptionHandler) {
                if (it.isNotBlank()) {
                    _state.value = contentState.copy(ticketsContent = TicketsContent(getTicketsUseCase(), it))
                } else {
                    _state.value = contentState.copy(ticketsContent = null)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadData() {
        if (_state.value is MainState.Content) {
            return
        }
        _state.value = MainState.Loading

        viewModelScope.launch(loadDataExceptionHandler) {
            _state.value = MainState.Content(getOffersUseCase(), null)
        }
    }

    fun navigateToTicketsScreen(ticketDirection: String, ticketDate: String) {
        router.openTicketsScreen(ticketDirection, ticketDate)
    }

}