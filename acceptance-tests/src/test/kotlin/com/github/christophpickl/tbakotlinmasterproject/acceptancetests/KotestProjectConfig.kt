package com.github.christophpickl.tbakotlinmasterproject.acceptancetests

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.Listener

@Suppress("unused")
object KotestProjectConfig : AbstractProjectConfig() {
    override fun listeners(): List<Listener> {
        return mutableListOf<Listener>().also {
            it.add(DatabaseMigrationListener)
            if (TestEnvironment.current == TestEnvironment.Local) {
                it.add(KtorAppStarter(TestEnvironment.localPort))
            }
        }
    }
}
