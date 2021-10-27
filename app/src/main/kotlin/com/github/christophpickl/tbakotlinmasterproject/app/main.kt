@file:JvmName("Main")

package com.github.christophpickl.tbakotlinmasterproject.app

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080) {
        configureKtor()
    }.start(wait = true)
}
