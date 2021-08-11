package com.github.christophpickl.tbakotlinmasterproject

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        this.main()
    }.start(wait = true)
}

fun Application.main() {
    routing {
        get("/") {
            call.respondText("Hello World")
        }
    }
}
