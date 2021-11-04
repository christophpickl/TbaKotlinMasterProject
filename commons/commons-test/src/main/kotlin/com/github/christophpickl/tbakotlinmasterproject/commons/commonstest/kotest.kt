package com.github.christophpickl.tbakotlinmasterproject.commons.commonstest

import io.kotest.core.listeners.TestListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.mockk.clearAllMocks
import io.mockk.unmockkAll

object MockListener : TestListener {
    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        clearAllMocks()
        unmockkAll()
    }
}
