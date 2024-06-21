package com.example.aviatickets.presentation.ui

import android.app.Application
import com.example.aviatickets.di.navigationModule
import com.example.aviatickets.di.networkModule
import com.example.main_screen.di.mainScreenModule
import com.example.tickets_screen.di.ticketsScreenModule
import org.koin.core.context.startKoin

class AviaTicketsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                navigationModule,
                networkModule,
                mainScreenModule,
                ticketsScreenModule
            )
        }
    }
}