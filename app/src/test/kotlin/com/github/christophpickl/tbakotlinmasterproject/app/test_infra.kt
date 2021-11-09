package com.github.christophpickl.tbakotlinmasterproject.app

import io.kotest.core.spec.style.scopes.DescribeSpecContainerContext
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.withTestApplication
import org.koin.core.module.Module

fun withTest(additionalModules: List<Module> = emptyList(), testCode: TestApplicationEngine.() -> Unit) {
    withTestApplication({
        configureKtor(AppConfig(), additionalModules)
    }, testCode)
}

suspend fun DescribeSpecContainerContext.test(
    name: String,
    additionalModules: List<Module> = emptyList(),
    enabled: Boolean = true,
    testCode: TestApplicationEngine.() -> Unit
) {
    it(name).config(enabled = enabled) {
        withTest(additionalModules) {
            testCode()
        }
    }
}
