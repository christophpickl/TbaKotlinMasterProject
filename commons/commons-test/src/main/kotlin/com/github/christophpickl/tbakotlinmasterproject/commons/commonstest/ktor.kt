package com.github.christophpickl.tbakotlinmasterproject.commons.commonstest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.spec.style.scopes.DescribeSpecContainerContext
import io.kotest.core.spec.style.scopes.DescribeSpecRootContext
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.handleRequest

fun TestApplicationEngine.handleGet(path: String) =
    handleRequest(HttpMethod.Get, path).response

fun TestApplicationResponse.statusShouldBeOk() {
    status() shouldBe HttpStatusCode.OK
}

fun DescribeSpecRootContext.route(path: String, test: suspend DescribeSpecContainerContext.() -> Unit) =
    describe(path, test)

val jackson = jacksonObjectMapper()

inline fun <reified T> TestApplicationResponse.contentAs(): T {
    content.shouldNotBeNull()
    return jackson.readValue(content!!)
}
