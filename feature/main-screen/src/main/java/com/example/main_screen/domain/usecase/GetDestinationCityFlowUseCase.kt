package com.example.main_screen.domain.usecase

import com.example.main_screen.domain.repository.DestinationCityRepository
import kotlinx.coroutines.flow.SharedFlow

class GetDestinationCityFlowUseCase(private val repository: DestinationCityRepository) {

    operator fun invoke(): SharedFlow<String> {
        return repository.getFlow()
    }
}