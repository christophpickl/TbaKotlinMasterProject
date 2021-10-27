package com.github.christophpickl.tbakotlinmasterproject.app

import arrow.core.Either
import com.github.christophpickl.tbakotlinmasterproject.apimodel.Rto
import com.github.christophpickl.tbakotlinmasterproject.commonsktor.installMoshi
import com.github.christophpickl.tbakotlinmasterproject.commonsktor.installRoutes
import com.github.christophpickl.tbakotlinmasterproject.domainlogic.domainLogicModule
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Fault
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.DefaultHeaders
import io.ktor.response.respond
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
            add(appModule())
            // wire boundary module and pass some (raw) config values
            log.debug { "Adding additional koin modules: $additionalModules" }
            addAll(additionalModules)
        })
    }
    installMoshi()
    installRoutes()
}

suspend inline fun <reified RTO : Rto> ApplicationCall.respondEither(result: Either<Fault, RTO>) {
    result.fold(
        ifLeft = {
            respondFault(it)
        },
        ifRight = {
            respond(it)
        }
    )
}
