package com.github.christophpickl.tbakotlinmasterproject.acceptance.tests

import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.KtorHttpRequester
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpStatusCode

class AcceptanceRoutesClient(
    baseUrl: String
) {
    private val client = KtorHttpRequester(baseUrl)

    suspend fun requestCreateTables() {
        client.GET("/acceptanceTest/createTables").status shouldBe HttpStatusCode.OK
    }

    suspend fun requestDeleteAll() {
        client.GET("/acceptanceTest/deleteAll").status shouldBe HttpStatusCode.OK
    }

//    private infix fun HttpStatusCode.shouldBe(expected: HttpStatusCode) {
//        if(this != expected) {
//            throw RuntimeException("Expected $expected but got $this!")
//        }
//    }
}