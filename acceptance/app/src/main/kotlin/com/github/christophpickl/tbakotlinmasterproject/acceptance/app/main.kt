package com.github.christophpickl.tbakotlinmasterproject.acceptance.app

import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.readRuntimeVariableOrThrow
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mu.KotlinLogging.logger

object AcceptanceApp {

    private val log = logger {}
    private const val portKey = "ACCEPTANCE_APP_PORT"

    @JvmStatic
    fun main(args: Array<String>) {
        val port = readRuntimeVariableOrThrow(portKey).toInt()
        log.info { "Starting up acceptance application on port: $port" }
        embeddedServer(Netty, port = port) {
            configureKtor()
        }.start(wait = true)
    }
}
