package com.github.christophpickl.tbakotlinmasterproject.app

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.DefaultHeaders

fun Application.configureKtor() {
    install(CallLogging) {
        filter { true }
    }
    install(Compression)
    install(DefaultHeaders)
    installMoshi()
    installRoutes()
}
