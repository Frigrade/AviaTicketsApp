package com.example.main_screen.domain.entity

import com.google.gson.annotations.SerializedName

data class TicketsList(@SerializedName("tickets_offers") val tickets: List<TicketOffer>)

data class TicketOffer(
    val id: Int,
    val title: String,
    @SerializedName("time_range")
    val timeRange: List<String>,
    val price: Price
)