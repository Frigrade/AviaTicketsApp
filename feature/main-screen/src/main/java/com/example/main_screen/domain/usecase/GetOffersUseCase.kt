package com.example.main_screen.domain.usecase

import com.example.main_screen.domain.entity.Offer
import com.example.main_screen.domain.repository.OffersRepository

class GetOffersUseCase(
    private val offersRepository: OffersRepository
) {
    suspend operator fun invoke(): List<Offer> = offersRepository.getOffers()
}