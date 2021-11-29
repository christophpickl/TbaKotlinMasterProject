package com.github.christophpickl.tbakotlinmasterproject.acceptance.app

import com.github.christophpickl.tbakotlinmasterproject.acceptance.routes.AcceptanceTestRoute
import com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb.DatabaseConfig
import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.Route
import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.installMoshi
import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.installRoutes
import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.Modules
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.DefaultHeaders
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.ext.Koin

fun Application.configureKtor() {
    installKoin()
    install(CallLogging) {
        filter { true }
    }
    install(Compression)
    install(DefaultHeaders)
    installMoshi()
    installRoutes()
}

private fun Application.installKoin() {
    val databaseConfig = DatabaseConfig(
        jdbcUrl = "jdbc:h2:mem:acceptanceDb;DB_CLOSE_DELAY=-1;",
        driverClassName = "org.h2.Driver",
        username = "",
        password = ""
    )
    install(Koin) {
        modules(
            Modules.acceptanceApp(),
            module {
                single { databaseConfig.connectDatabase() }
                single { AcceptanceTestRoute(get()) } bind Route::class
            }
        )
    }
}
