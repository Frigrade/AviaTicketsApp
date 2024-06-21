package com.example.main_screen.domain.usecase

import com.example.main_screen.domain.repository.DestinationCityRepository

class EmitDestinationCityUseCase(private val repository: DestinationCityRepository) {

    suspend operator fun invoke(text: String) {
        repository.emitText(text)
    }
}