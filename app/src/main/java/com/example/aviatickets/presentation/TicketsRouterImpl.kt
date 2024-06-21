package com.example.aviatickets.presentation

import com.example.tickets_screen.presentation.TicketsRouter
import com.github.terrakok.cicerone.Router

class TicketsRouterImpl(private val router: Router) : TicketsRouter {

    override fun navigateBack() {
        router.exit()
    }
}