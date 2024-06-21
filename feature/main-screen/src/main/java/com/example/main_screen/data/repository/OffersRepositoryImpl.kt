package com.example.main_screen.data.repository

import com.example.main_screen.domain.entity.Offer
import com.example.main_screen.domain.network.MainApi
import com.example.main_screen.domain.repository.OffersRepository

class OffersRepositoryImpl(private val mainApi: MainApi) : OffersRepository {

    override suspend fun getOffers(): List<Offer> = mainApi.getOffers().offers
}