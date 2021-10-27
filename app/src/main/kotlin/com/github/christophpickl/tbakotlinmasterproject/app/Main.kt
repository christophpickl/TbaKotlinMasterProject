@file:JvmName("Main")

package com.github.christophpickl.tbakotlinmasterproject.app

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080) {
        main()
    }.start(wait = true)
}

fun Application.main() {
    routing {
        get("/") {
            call.respondText("Hello World")
        }
    }
}
