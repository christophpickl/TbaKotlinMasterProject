package com.github.christophpickl.tbakotlinmasterproject.commonsktor

import io.ktor.application.Application
import io.ktor.routing.Routing
import io.ktor.routing.routing
import mu.KotlinLogging.logger
import org.koin.java.KoinJavaComponent.getKoin

private val log = logger {}

interface Route {
    fun Routing.install()
    fun installOn(routing: Routing) {
        routing.install()
    }
}

fun Application.installRoutes() {
    log.info { "Installing routes." }
    
    val routes = getKoin().getAll<Route>().distinct() // strangely getting duplicate instances here?!
    log.debug { "Registered ${routes.size} routes: ${routes.joinToString()}" }
    
    routing {
        routes.forEach {
            it.installOn(this)
        }
    }
}
