package com.example.main_screen.domain.network

import com.example.tickets_screen.domain.entity.Tickets
import retrofit2.http.GET

interface TicketsApi {

    @GET("c0464573-5a13-45c9-89f8-717436748b69")
    suspend fun getTickets(): Tickets
}