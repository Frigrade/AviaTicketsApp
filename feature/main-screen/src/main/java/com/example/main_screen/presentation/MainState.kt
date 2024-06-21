package com.example.main_screen.presentation

import com.example.main_screen.domain.entity.Offer
import com.example.main_screen.domain.entity.TicketOffer

sealed class MainState {

    data object Loading : MainState()

    data object InitialLoadError : MainState()

    data class Content(
        val offerList: List<Offer>,
        val ticketsContent: TicketsContent?,
    ): MainState()
}

data class TicketsContent(val ticketList: List<TicketOffer>, val destinationCity: String)