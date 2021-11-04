package com.github.christophpickl.tbakotlinmasterproject.app

import arrow.core.flatMap
import arrow.core.right
import com.github.christophpickl.tbakotlinmasterproject.api.apimodel.AuctionRto
import com.github.christophpickl.tbakotlinmasterproject.api.apimodel.AuctionsRto
import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.Route
import com.github.christophpickl.tbakotlinmasterproject.domain.domainlogic.AuctionService
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auction
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get

class HomeRoute() : Route {
    override fun Routing.install() {
        get("/") {
            call.respondText("Hello World")
        }
    }
}

class AuctionRoute(
    private val auctionService: AuctionService
) : Route {
    
    override fun Routing.install() {
        get("/auctions") {
            val auctions = auctionService.loadAll().flatMap {
                AuctionsRto(auctions = it.map { auction ->
                    auction.toAuctionRto()
                }).right()
            }
            call.respondEither(auctions)
        }
    }

    private fun Auction.toAuctionRto() = AuctionRto(
        title = title.value
    )
}
