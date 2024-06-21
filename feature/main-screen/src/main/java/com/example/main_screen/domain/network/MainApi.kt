package com.example.main_screen.domain.network

import com.example.main_screen.domain.entity.OfferList
import com.example.main_screen.domain.entity.TicketsList
import retrofit2.http.GET

interface MainApi {

    @GET("ad9a46ba-276c-4a81-88a6-c068e51cce3a")
    suspend fun getOffers(): OfferList

    @GET("38b5205d-1a3d-4c2f-9d77-2f9d1ef01a4a")
    suspend fun getTickets(): TicketsList

}