package com.example.main_screen.data.repository

import com.example.main_screen.domain.entity.TicketOffer
import com.example.main_screen.domain.network.MainApi
import com.example.main_screen.domain.repository.TicketsRepository

class TicketsRepositoryImpl(private val mainApi: MainApi) : TicketsRepository {

    override suspend fun getTickets(): List<TicketOffer> = mainApi.getTickets().tickets
}