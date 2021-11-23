package com.github.christophpickl.tbakotlinmasterproject.acceptancetests

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.Listener

@Suppress("unused")
object KotestProjectConfig : AbstractProjectConfig() {
    override fun listeners(): List<Listener> =
        mutableListOf<Listener>().also {
            if (TestEnvironment.current == TestEnvironment.Local) {
                it.add(LocalInfraListener(LocalInfraManager(TestEnvironment.localPort)))
            }
        }
}

