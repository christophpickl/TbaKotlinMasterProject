package com.github.christophpickl.tbakotlinmasterproject.acceptance.tests

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.Listener

@Suppress("unused")
object KotestProjectConfig : AbstractProjectConfig() {
    override fun listeners(): List<Listener> =
        mutableListOf<Listener>().also {
            if (TestEnvironment.current == TestEnvironment.Local) {
                it.add(LocalInfraListener(LocalInfraManager(TestEnvironment.localPort, AcceptanceRoutesClient(TestEnvironment.current.baseUrl))))
            }
            // when running in docker-compose, acceptance routes are on another base URL!
        }
}
