package com.example.tickets_screen.data.repository

import com.example.main_screen.domain.network.TicketsApi
import com.example.tickets_screen.domain.entity.Ticket
import com.example.tickets_screen.domain.repository.TicketsRepository

class TicketsRepositoryImpl(private val ticketsApi: TicketsApi) : TicketsRepository {

    override suspend fun getTickets(): List<Ticket> = ticketsApi.getTickets().tickets
}