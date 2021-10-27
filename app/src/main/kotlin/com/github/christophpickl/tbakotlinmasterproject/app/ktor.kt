package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.commonsktor.installMoshi
import com.github.christophpickl.tbakotlinmasterproject.domainlogic.domainLogicModule
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.DefaultHeaders
import mu.KotlinLogging.logger
import org.koin.core.module.Module
import org.koin.ktor.ext.Koin

private val log = logger {}

fun Application.configureKtor(config: AppConfig, additionalModules: List<Module> = emptyList()) {
    install(CallLogging) {
        filter { true }
    }
    install(Compression)
    install(DefaultHeaders)
    install(Koin) {
        modules(mutableListOf<Module>().apply {
            add(domainLogicModule())
            // wire boundary module and pass some (raw) config values
            log.debug { "Adding additional koin modules: $additionalModules" }
            addAll(additionalModules)
        })
    }
    installMoshi()
    installRoutes()
}
