package com.github.christophpickl.tbakotlinmasterproject.app

import arrow.core.left
import arrow.core.right
import com.github.christophpickl.tbakotlinmasterproject.api.apimodel.AuctionRto
import com.github.christophpickl.tbakotlinmasterproject.api.apimodel.AuctionsRto
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.contentAs
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.handleGet
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.route
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.statusShouldBeOk
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.testModule
import com.github.christophpickl.tbakotlinmasterproject.domain.domainlogic.AuctionService
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auctions
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.InternalFault
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.any
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.mockk

class AuctionsRouteTest : DescribeSpec() {
    
    private val auction = Auction.any()
    private val internalFault = InternalFault.any()
    private val auctionService = mockk<AuctionService>()
    
    init {
        route("/auctions") {
            
            test("Given an auction Then ok and return it", testModule { single { auctionService } }) {
                coEvery { auctionService.loadAll() } returns Auctions(listOf(auction)).right()
        
                val response = handleGet("/auctions")
        
                response.statusShouldBeOk()
                response.contentAs<AuctionsRto>() shouldBe AuctionsRto(listOf(AuctionRto(auction.title.value)))
            }
            
            test("Given unknown fault Then error", testModule { single { auctionService } }) {
                coEvery { auctionService.loadAll() } returns internalFault.left()
        
                val response = handleGet("/auctions")
        
                response.status() shouldBe HttpStatusCode.InternalServerError
            }
        }
    }
}
