package com.github.christophpickl.tbakotlinmasterproject.acceptancetests

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import mu.KotlinLogging.logger

@Suppress("FunctionName")
object Http {

    private val log = logger {}

    suspend fun GET(path: String): HttpResponse = request(Method.GET, path)

    suspend fun request(method: Method, path: String): HttpResponse =
        HttpClient(CIO) {
            expectSuccess = false
        }.use {
            val fullUrl = "${TestEnvironment.current.baseUrl}$path"
            log.debug { "Requesting: $method $fullUrl" }
            it.request(fullUrl) {
                this.method = method.asHttpMethod
            }
        }

    private val Method.asHttpMethod: HttpMethod get() = when(this) {
        Method.GET -> HttpMethod.Get
        Method.POST -> HttpMethod.Post
    }
}

enum class Method {
    GET,
    POST
}
