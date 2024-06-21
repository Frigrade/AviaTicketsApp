package com.example.tickets_screen.di

import com.example.main_screen.domain.network.TicketsApi
import com.example.tickets_screen.data.repository.TicketsRepositoryImpl
import com.example.tickets_screen.domain.repository.TicketsRepository
import com.example.tickets_screen.domain.usecase.GetTicketsUseCase
import com.example.tickets_screen.presentation.TicketsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val ticketsScreenModule = module {
    single { provideTicketsService(get()) }

    factory<TicketsRepository> { TicketsRepositoryImpl(get()) }
    factory { GetTicketsUseCase(get()) }

    viewModel { TicketsViewModel(get(), get()) }
}

fun provideTicketsService(retrofit: Retrofit): TicketsApi =
    retrofit.create(TicketsApi::class.java)