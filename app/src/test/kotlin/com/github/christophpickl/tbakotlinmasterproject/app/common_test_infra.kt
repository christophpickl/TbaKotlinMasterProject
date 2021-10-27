package com.github.christophpickl.tbakotlinmasterproject.app

import io.kotest.core.spec.style.scopes.DescribeSpecContainerContext
import io.kotest.core.spec.style.scopes.DescribeSpecRootContext
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.handleRequest
import org.koin.core.module.Module

fun TestApplicationEngine.handleGet(path: String) =
    handleRequest(HttpMethod.Get, path).response

fun TestApplicationResponse.statusShouldBeOk() {
    status() shouldBe HttpStatusCode.OK
}

fun DescribeSpecRootContext.route(path: String, test: suspend DescribeSpecContainerContext.() -> Unit) = describe(path, test)

suspend fun DescribeSpecContainerContext.test(name: String, vararg modules: Module, test: TestApplicationEngine.() -> Unit) {
    it(name) {
        withTest(*modules) {
            test()
        }
    }
}
