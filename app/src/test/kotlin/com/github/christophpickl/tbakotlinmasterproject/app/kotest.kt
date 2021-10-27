package com.github.christophpickl.tbakotlinmasterproject.app

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.Listener
import io.kotest.core.listeners.TestListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.mockk.clearAllMocks
import io.mockk.unmockkAll

@Suppress("unused")
object KotestProjectConfig : AbstractProjectConfig() {
    override fun listeners(): List<Listener> = listOf(
        MockListener,
    )
}

object MockListener : TestListener {
    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        clearAllMocks()
        unmockkAll()
    }
}
