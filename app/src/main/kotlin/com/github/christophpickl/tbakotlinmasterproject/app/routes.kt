package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.apimodel.AuctionRto
import com.github.christophpickl.tbakotlinmasterproject.apimodel.AuctionsRto
import com.github.christophpickl.tbakotlinmasterproject.domainlogic.Auctions
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Auction
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import mu.KotlinLogging.logger
import org.koin.ktor.ext.inject

private val log = logger {}

fun Application.installRoutes() {
    log.debug { "Installing routes." }
    val auctions by inject<Auctions>()
    
    routing {
        get("/") {
            log.debug { "Root route was called." }
            call.respondText("Hello World")
        }
        get("/auctions") {
            call.respond(AuctionsRto(auctions.getAll().map { it.toAuctionRto() }))
        }
    }
}

private fun Auction.toAuctionRto() = AuctionRto(
    title = title.value
)
