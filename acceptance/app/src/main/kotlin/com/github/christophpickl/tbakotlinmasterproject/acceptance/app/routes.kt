package com.github.christophpickl.tbakotlinmasterproject.acceptance.app

import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.Route
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get

class HomeRoute : Route {
    override fun Routing.install() {
        get("/") {
            call.respondText("Hello Acceptance-App")
        }
    }
}
