package com.example.aviatickets.presentation

import com.example.aviatickets.presentation.ui.stub.StubDestination
import com.example.main_screen.presentation.MainRouter
import com.example.tickets_screen.TicketsFragmentDestination
import com.github.terrakok.cicerone.Router

class MainRouterImpl(private val router: Router) : MainRouter {

    override fun openTicketsScreen(ticketDirection: String, ticketDate: String) {
        router.navigateTo(TicketsFragmentDestination(ticketDirection, ticketDate))
    }

    override fun navigateToStub() {
        router.navigateTo(StubDestination())
    }
}