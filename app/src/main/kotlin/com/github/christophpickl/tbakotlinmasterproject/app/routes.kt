package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.apimodel.AuctionRto
import com.github.christophpickl.tbakotlinmasterproject.apimodel.AuctionsRto
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import mu.KotlinLogging.logger

private val log = logger {}

fun Application.installRoutes() {
    log.debug { "Installing routes." }
    
    routing {
        get("/") {
            log.debug { "Root route was called." }
            call.respondText("Hello World")
        }
        get("/auctions") {
            call.respond(AuctionsRto(listOf(AuctionRto("my auction"))))
        }
    }
}
