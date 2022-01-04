package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.Route
import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.Modules
import org.koin.dsl.bind
import org.koin.dsl.module

@Suppress("unused")
fun Modules.app() = module {
    single { HomeRoute() } bind Route::class
    single { AuctionRoute(get()) } bind Route::class
}
