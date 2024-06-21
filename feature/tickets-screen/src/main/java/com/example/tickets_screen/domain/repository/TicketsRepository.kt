package com.example.tickets_screen.domain.repository

import com.example.tickets_screen.domain.entity.Ticket

interface TicketsRepository {

    suspend fun getTickets(): List<Ticket>
}