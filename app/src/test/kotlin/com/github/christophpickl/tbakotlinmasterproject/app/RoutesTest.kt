package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.apimodel.AuctionRto
import com.github.christophpickl.tbakotlinmasterproject.apimodel.AuctionsRto
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.scopes.DescribeSpecContainerContext
import io.kotest.core.spec.style.scopes.DescribeSpecRootContext
import io.kotest.matchers.shouldBe
import io.ktor.server.testing.TestApplicationEngine

class KtorTest : DescribeSpec() {
    init {
        route("/") {
            test("Ok and returns greeting") {
                val response = handleGet("/")
    
                response.statusShouldBeOk()
                response.content shouldBe "Hello World"
            }
        }
        route("/auctions") {
            test("Ok and returns data") {
                val response = handleGet("/auctions")
    
                response.statusShouldBeOk()
                response.contentAs<AuctionsRto>() shouldBe AuctionsRto(listOf(AuctionRto("my auction")))
            }
        }
    }
}
