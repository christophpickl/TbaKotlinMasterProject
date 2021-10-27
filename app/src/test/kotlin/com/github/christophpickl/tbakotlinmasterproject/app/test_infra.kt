package com.github.christophpickl.tbakotlinmasterproject.app

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.withTestApplication
import org.koin.core.module.Module

fun withTest(vararg modules: Module, test: TestApplicationEngine.() -> Unit) {
    withTestApplication({
        configureKtor(AppConfig(), modules.toList())
    }, test)
}

val jackson = jacksonObjectMapper()

inline fun <reified T> TestApplicationResponse.contentAs(): T {
    content.shouldNotBeNull()
    return jackson.readValue(content!!)
}
