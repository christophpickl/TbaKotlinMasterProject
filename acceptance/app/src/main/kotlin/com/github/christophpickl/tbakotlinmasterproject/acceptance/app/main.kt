package com.github.christophpickl.tbakotlinmasterproject.acceptance.app

import com.github.christophpickl.tbakotlinmasterproject.acceptance.routes.AcceptanceTestRoute
import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.Route
import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.installMoshi
import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.installRoutes
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.DefaultHeaders
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mu.KotlinLogging.logger
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.ext.Koin

private val log = logger {}
private val port = 8188 // TODO read from env, passed by docker-compose

fun main() {
    log.info { "Starting up acceptance application on port $port." }
    embeddedServer(Netty, port = port) {
        configureKtor()
    }.start(wait = true)
}

private fun Application.configureKtor() {
    install(CallLogging) {
        filter { true }
    }
    install(Compression)
    install(DefaultHeaders)
    installMoshi()
    installRoutes()
    installKoin()
}

private fun Application.installKoin() {
    install(Koin) {
        modules(
            module {
                single { AcceptanceTestRoute(get()) } bind Route::class
            }
        )
    }
}