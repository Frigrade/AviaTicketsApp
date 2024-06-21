package com.example.tickets_screen.domain.entity

import com.google.gson.annotations.SerializedName

data class Ticket(
    val arrival: Arrival,
    val badge: String?,
    val company: String,
    val departure: Departure,
    @SerializedName ("hand_luggage")
    val hand_luggage: HandLuggage,
    @SerializedName ("has_transfer")
    val hasTransfer: Boolean,
    @SerializedName ("has_visa_transfer")
    val hasVisaTransfer: Boolean,
    val id: Int,
    @SerializedName ("is_exchangable")
    val isExchangeable: Boolean,
    @SerializedName ("is_returnable")
    val isReturnable: Boolean,
    val luggage: Luggage,
    val price: PriceX,
    @SerializedName ("provider_name")
    val providerName: String
)