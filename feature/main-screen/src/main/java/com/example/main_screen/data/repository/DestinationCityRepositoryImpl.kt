package com.example.main_screen.data.repository

import com.example.main_screen.domain.repository.DestinationCityRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class DestinationCityRepositoryImpl : DestinationCityRepository {

    private val flow = MutableSharedFlow<String>(replay = 1)

    override fun getFlow() = flow.asSharedFlow()

    override suspend fun emitText(text: String) {
        flow.emit(text)
    }
}