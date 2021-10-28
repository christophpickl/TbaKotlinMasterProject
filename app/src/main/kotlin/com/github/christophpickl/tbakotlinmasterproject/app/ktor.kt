package com.github.christophpickl.tbakotlinmasterproject.app

import arrow.core.Either
import com.github.christophpickl.tbakotlinmasterproject.apimodel.Rto
import com.github.christophpickl.tbakotlinmasterproject.boundarydb.boundaryDb
import com.github.christophpickl.tbakotlinmasterproject.commonsktor.installMoshi
import com.github.christophpickl.tbakotlinmasterproject.commonsktor.installRoutes
import com.github.christophpickl.tbakotlinmasterproject.commonstest.Modules
import com.github.christophpickl.tbakotlinmasterproject.domainlogic.domainLogic
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
    installKoin(additionalModules)
    installMoshi()
    installRoutes()
}

private fun Application.installKoin(additionalModules: List<Module>) {
    install(Koin) {
        modules(mutableListOf<Module>().apply {
            add(Modules.boundaryDb())
            add(Modules.domainLogic())
            add(Modules.app())
            // TODO wire boundary module and pass some (raw) config values
            log.debug { "Adding ${additionalModules.size} additional koin modules." }
            addAll(additionalModules)
        })
    }
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
