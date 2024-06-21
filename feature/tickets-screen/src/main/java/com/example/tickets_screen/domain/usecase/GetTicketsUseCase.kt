package com.example.tickets_screen.domain.usecase

import com.example.tickets_screen.domain.entity.Ticket
import com.example.tickets_screen.domain.repository.TicketsRepository

class GetTicketsUseCase(
    private val ticketsRepository: TicketsRepository
) {
    suspend operator fun invoke(): List<Ticket> = ticketsRepository.getTickets()
}