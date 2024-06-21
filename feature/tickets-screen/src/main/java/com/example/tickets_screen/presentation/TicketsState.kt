package com.example.tickets_screen.presentation

import com.example.tickets_screen.domain.entity.Ticket

sealed class TicketsState {

    data object Loading : TicketsState()

    data object Error : TicketsState()

    data class Content(val ticketList: List<Ticket>): TicketsState()
}
