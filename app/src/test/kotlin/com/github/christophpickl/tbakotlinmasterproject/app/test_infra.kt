package com.github.christophpickl.tbakotlinmasterproject.app

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.ktor.application.Application
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.withTestApplication

fun withTest(test: TestApplicationEngine.() -> Unit) {
    withTestApplication(Application::configureKtor, test)
}

val jackson = jacksonObjectMapper()

inline fun <reified T> TestApplicationResponse.contentAs(): T {
    content.shouldNotBeNull()
    return jackson.readValue(content!!)
}
