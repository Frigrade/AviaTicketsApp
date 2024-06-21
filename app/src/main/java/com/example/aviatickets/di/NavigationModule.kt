package com.example.aviatickets.di

import com.example.aviatickets.presentation.MainRouterImpl
import com.example.aviatickets.presentation.StubRouterImpl
import com.example.aviatickets.presentation.TicketsRouterImpl
import com.example.main_screen.presentation.MainRouter
import com.example.tickets_screen.presentation.TicketsRouter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module

val navigationModule = module {
    single<Cicerone<Router>> { Cicerone.create() }
    single<Router> { get<Cicerone<Router>>().router }
    single<NavigatorHolder> { get<Cicerone<Router>>().getNavigatorHolder() }

    factory<MainRouter> { MainRouterImpl(get()) }
    factory<TicketsRouter> { TicketsRouterImpl(get()) }

    factory { StubRouterImpl(get()) }
}