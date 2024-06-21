package com.example.tickets_screen.domain.entity

import com.google.gson.annotations.SerializedName

data class Luggage(
    @SerializedName ("has_luggage")
    val hasLuggage: Boolean,
    val price: PriceX
)