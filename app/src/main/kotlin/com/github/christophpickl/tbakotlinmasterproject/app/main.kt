@file:JvmName("Main")

package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb.DatabaseConfig
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mu.KotlinLogging.logger

private val log = logger {}

fun main() {
    val config = AppConfig(
        port = 8080,
        dbConfig = DatabaseConfig(
            // FIXME read from env.
            jdbcUrl = "",
            driverClassName = "",
            username = "",
            password = ""
        )
    )
    log.info { "Starting up application: $config" }
    embeddedServer(Netty, port = config.port) {
        configureKtor(config)
    }.start(wait = true)
}

data class AppConfig(
    val port: Int,
    val dbConfig: DatabaseConfig
)
