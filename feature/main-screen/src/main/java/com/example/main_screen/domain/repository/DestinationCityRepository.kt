package com.example.main_screen.domain.repository

import kotlinx.coroutines.flow.SharedFlow

interface DestinationCityRepository {

    fun getFlow(): SharedFlow<String>

    suspend fun emitText(text: String)
}