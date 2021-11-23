@file:JvmName("Main")

package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb.DatabaseConfig
import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.readRuntimeVariableOrThrow
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mu.KotlinLogging.logger

private val log = logger {}

fun main() {
    val config = AppConfig(
        port = 8080,
        dbConfig = DatabaseConfig(
            jdbcUrl = readRuntimeVariableOrThrow("KMP_DB_URL"),
            driverClassName = readRuntimeVariableOrThrow("KMP_DB_DRIVER"),
            username = readRuntimeVariableOrThrow("KMP_DB_USERNAME"),
            password = readRuntimeVariableOrThrow("KMP_DB_PASSWORD")
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
