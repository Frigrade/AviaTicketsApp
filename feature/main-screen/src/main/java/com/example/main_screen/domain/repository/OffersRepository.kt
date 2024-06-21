package com.example.main_screen.domain.repository

import com.example.main_screen.domain.entity.Offer

interface OffersRepository {

    suspend fun getOffers(): List<Offer>
}