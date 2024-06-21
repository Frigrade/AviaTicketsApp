package com.example.main_screen.presentation.ui.bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main_screen.domain.usecase.EmitDestinationCityUseCase
import com.example.main_screen.presentation.MainRouter
import kotlinx.coroutines.launch

class BottomSheetViewModel(
    private val router: MainRouter,
    private val emitDestinationCityUseCase: EmitDestinationCityUseCase
) : ViewModel() {

    fun handleDestinationCity(destinationCity: String?) {
        if (destinationCity != null) {
            viewModelScope.launch {
                emitDestinationCityUseCase(destinationCity)
            }
        }
    }

    fun navigateToStub() {
        router.navigateToStub()
    }
}