package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.commonsktor.Route
import com.github.christophpickl.tbakotlinmasterproject.commonstest.Modules
import org.koin.dsl.bind
import org.koin.dsl.module

fun Modules.app() = module {
    single { HomeRoute() } bind Route::class
    single { AuctionRoute(get()) } bind Route::class
}
