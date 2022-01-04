package com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import mu.KotlinLogging.logger

interface HttpRequester {
    suspend fun get(path: String): HttpResponse
    suspend fun request(method: Method, path: String): HttpResponse
}

@Suppress("FunctionName")
class KtorHttpRequester(
    private val baseUrl: String
) : HttpRequester {

    private val log = logger {}

    override suspend fun get(path: String): HttpResponse = request(Method.GET, path)

    override suspend fun request(method: Method, path: String): HttpResponse =
        HttpClient(CIO) {
            expectSuccess = false
        }.use {
            val fullUrl = "$baseUrl$path"
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
