package com.example.main_screen.domain.entity

data class OfferList(val offers: List<Offer>)

data class Offer(
    val id: Int,
    val title: String,
    val town: String,
    val price: Price
)