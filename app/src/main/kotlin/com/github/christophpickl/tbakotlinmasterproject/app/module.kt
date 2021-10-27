package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.commonsktor.Route
import org.koin.dsl.bind
import org.koin.dsl.module

fun appModule() = module {
    single { HomeRoute() } bind Route::class
    single { AuctionRoute(get()) } bind Route::class
}
