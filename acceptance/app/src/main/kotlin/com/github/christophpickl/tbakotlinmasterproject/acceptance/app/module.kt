package com.github.christophpickl.tbakotlinmasterproject.acceptance.app

import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.Route
import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.Modules
import org.koin.dsl.bind
import org.koin.dsl.module

@Suppress("unused")
fun Modules.acceptanceApp() = module {
    single { HomeRoute() } bind Route::class
}
