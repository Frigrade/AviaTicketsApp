package com.example.aviatickets.presentation

import com.example.tickets_screen.TicketsFragmentDestination
import com.github.terrakok.cicerone.Router

class StubRouterImpl(private val router: Router) {

    fun navigateBack() {
        router.exit()
    }
}