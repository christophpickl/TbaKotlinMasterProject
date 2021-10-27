package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.apimodel.AuctionRto
import com.github.christophpickl.tbakotlinmasterproject.apimodel.AuctionsRto
import com.github.christophpickl.tbakotlinmasterproject.commonstest.contentAs
import com.github.christophpickl.tbakotlinmasterproject.commonstest.handleGet
import com.github.christophpickl.tbakotlinmasterproject.commonstest.route
import com.github.christophpickl.tbakotlinmasterproject.commonstest.statusShouldBeOk
import com.github.christophpickl.tbakotlinmasterproject.domainlogic.Auctions
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.any
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.koin.dsl.module

class AuctionsRouteTest : DescribeSpec() {
    
    private val auction = Auction.any()
    private val auctions = mockk<Auctions>()
    
    init {
        route("/auctions") {
            test("Given an auction Then return it", module { single { auctions } }) {
                every { auctions.getAll() } returns listOf(auction)
                
                val response = handleGet("/auctions")
                
                response.statusShouldBeOk()
                response.contentAs<AuctionsRto>() shouldBe AuctionsRto(listOf(AuctionRto(auction.title.value)))
            }
        }
    }
}
