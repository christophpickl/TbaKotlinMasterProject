package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.api.apimodel.AuctionsRto
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.contentAs
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.handleGet
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.route
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.statusShouldBeOk
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class AuctionsRouteIntegrationTest : DescribeSpec() {
    init {
        route("/auctions") {
            test("Then return empty", enabled = false) {
                // FIXME fix DB config
                val response = handleGet("/auctions")

                response.statusShouldBeOk()
                response.contentAs<AuctionsRto>() shouldBe AuctionsRto(emptyList())
            }
        }
    }
}