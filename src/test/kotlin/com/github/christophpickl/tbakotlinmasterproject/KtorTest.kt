package com.github.christophpickl.tbakotlinmasterproject

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication

class KtorTest : StringSpec() {
    init {
        "foobar" {
            withTestApplication(Application::main) {
                handleRequest(HttpMethod.Get, "/").apply {
                    response.status() shouldBe HttpStatusCode.OK
                    response.content shouldBe "Hello World"
                }
            }
        }
    }
}
