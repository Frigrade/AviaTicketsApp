package com.example.main_screen.domain.repository

import com.example.main_screen.domain.entity.TicketOffer

interface TicketsRepository {

    suspend fun getTickets(): List<TicketOffer>
}