@file:JvmName("Main")

package com.github.christophpickl.tbakotlinmasterproject.app

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mu.KotlinLogging.logger

private val log = logger {}

fun main() {
    val config = AppConfig()
    log.info { "Starting up application: $config" }
    embeddedServer(Netty, port = config.port) {
        configureKtor(config)
    }.start(wait = true)
}

data class AppConfig(
    val port: Int = 8080
)
