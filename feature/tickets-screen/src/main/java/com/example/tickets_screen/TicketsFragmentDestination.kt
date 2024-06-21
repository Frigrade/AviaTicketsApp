package com.example.tickets_screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.tickets_screen.presentation.ui.TicketsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class TicketsFragmentDestination(
    private val ticketDirection: String,
    private val ticketDate: String
) : FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment {
        return TicketsFragment.getNewInstance(ticketDirection, ticketDate)
    }
}