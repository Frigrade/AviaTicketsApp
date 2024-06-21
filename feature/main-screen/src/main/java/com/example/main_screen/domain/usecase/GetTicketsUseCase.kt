package com.example.main_screen.domain.usecase

import com.example.main_screen.domain.entity.TicketOffer
import com.example.main_screen.domain.entity.TicketsList
import com.example.main_screen.domain.repository.TicketsRepository

class GetTicketsUseCase(
    private val ticketsRepository: TicketsRepository
) {
    suspend operator fun invoke(): List<TicketOffer> = ticketsRepository.getTickets()
}