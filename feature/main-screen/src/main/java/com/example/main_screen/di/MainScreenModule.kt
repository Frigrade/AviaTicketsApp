package com.example.main_screen.di

import com.example.main_screen.data.repository.DestinationCityRepositoryImpl
import com.example.main_screen.data.repository.OffersRepositoryImpl
import com.example.main_screen.data.repository.TicketsRepositoryImpl
import com.example.main_screen.domain.network.MainApi
import com.example.main_screen.domain.repository.DestinationCityRepository
import com.example.main_screen.domain.repository.OffersRepository
import com.example.main_screen.domain.repository.TicketsRepository
import com.example.main_screen.domain.usecase.EmitDestinationCityUseCase
import com.example.main_screen.domain.usecase.GetDestinationCityFlowUseCase
import com.example.main_screen.domain.usecase.GetOffersUseCase
import com.example.main_screen.domain.usecase.GetTicketsUseCase
import com.example.main_screen.presentation.MainViewModel
import com.example.main_screen.presentation.ui.bottom_sheet.BottomSheetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val mainScreenModule = module {
    single { provideService(get()) }

    factory<OffersRepository> { OffersRepositoryImpl(get()) }
    factory { GetOffersUseCase(get()) }

    factory<TicketsRepository> { TicketsRepositoryImpl(get()) }
    factory { GetTicketsUseCase(get()) }

    single<DestinationCityRepository> { DestinationCityRepositoryImpl() }
    factory { GetDestinationCityFlowUseCase(get()) }
    factory { EmitDestinationCityUseCase(get()) }

    viewModel { MainViewModel(get(), get(), get(), get()) }
    viewModel { BottomSheetViewModel(get(), get()) }

}

fun provideService(retrofit: Retrofit): MainApi =
    retrofit.create(MainApi::class.java)