package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.MockListener
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.Listener

@Suppress("unused")
object KotestProjectConfig : AbstractProjectConfig() {
    override fun listeners(): List<Listener> = listOf(
        MockListener
    )
}
