package com.github.christophpickl.tbakotlinmasterproject.app

import io.kotest.core.spec.style.scopes.DescribeSpecContainerContext
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.withTestApplication
import org.koin.core.module.Module

fun withTest(vararg modules: Module, test: TestApplicationEngine.() -> Unit) {
    withTestApplication({
        configureKtor(AppConfig(), modules.toList())
    }, test)
}

suspend fun DescribeSpecContainerContext.test(name: String, vararg modules: Module, test: TestApplicationEngine.() -> Unit) {
    it(name) {
        withTest(*modules) {
            test()
        }
    }
}
