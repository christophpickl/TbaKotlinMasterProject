package com.github.christophpickl.tbakotlinmasterproject.acceptancetests

import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.Tags
import io.kotest.assertions.json.shouldContainJsonKey
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.call.receive
import io.ktor.http.HttpStatusCode

class AuctionsRouteAcceptanceTest : DescribeSpec() {

    override fun tags() = setOf(Tags.RequiresPostgresTestcontainers, Tags.ReadOnly)

    init {
        describe("When GET /auctions") {
            it("Then return OK and empty JSON") {
                val response = Http.GET("/auctions")

                response.status shouldBe HttpStatusCode.OK
                val json = response.receive<String>()
                json shouldContainJsonKey "auctions"
                // json shouldMatchJson """{ "auctions": [] }"""
            }
        }
    }
}
