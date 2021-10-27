package com.github.christophpickl.tbakotlinmasterproject.app

import arrow.core.left
import arrow.core.right
import com.github.christophpickl.tbakotlinmasterproject.apimodel.AuctionRto
import com.github.christophpickl.tbakotlinmasterproject.apimodel.AuctionsRto
import com.github.christophpickl.tbakotlinmasterproject.commonstest.contentAs
import com.github.christophpickl.tbakotlinmasterproject.commonstest.handleGet
import com.github.christophpickl.tbakotlinmasterproject.commonstest.route
import com.github.christophpickl.tbakotlinmasterproject.commonstest.statusShouldBeOk
import com.github.christophpickl.tbakotlinmasterproject.domainlogic.AuctionService
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.InternalFault
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.any
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.mockk
import org.koin.dsl.module

class AuctionsRouteTest : DescribeSpec() {
    
    private val auction = Auction.any()
    private val internalFault = InternalFault.any()
    private val auctions = mockk<AuctionService>()
    
    init {
        route("/auctions") {
            
            test("Given an auction Then ok and return it", module { single { auctions } }) {
                coEvery { auctions.getAll() } returns listOf(auction).right()
        
                val response = handleGet("/auctions")
        
                response.statusShouldBeOk()
                response.contentAs<AuctionsRto>() shouldBe AuctionsRto(listOf(AuctionRto(auction.title.value)))
            }
            
            test("Given unknown fault Then error", module { single { auctions } }) {
                coEvery { auctions.getAll() } returns internalFault.left()
        
                val response = handleGet("/auctions")
        
                response.status() shouldBe HttpStatusCode.InternalServerError
            }
        }
    }
}
