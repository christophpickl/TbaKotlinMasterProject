package com.github.christophpickl.tbakotlinmasterproject.acceptancetests

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.call.receive
import io.ktor.http.HttpStatusCode

class AuctionsRouteAcceptanceTest : DescribeSpec() {
    // TODO tag tests with: { Read (for PROD), Write, TestEndpoint }
    init {
        describe("When GET /auctions") {
            it("Then return some") {
                val response = Http.GET("/auctions")

                response.status shouldBe HttpStatusCode.OK
                // TODO structural JSON comparison
                response.receive<String>() shouldBe """{"auctions":[]}"""
            }
        }
    }
}
