package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.apimodel.AuctionRto
import com.github.christophpickl.tbakotlinmasterproject.apimodel.AuctionsRto
import com.github.christophpickl.tbakotlinmasterproject.domainlogic.Auctions
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Title
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.koin.dsl.module

class AuctionsRouteTest : DescribeSpec() {
    
    private val title = "test title"
    
    init {
        route("/auctions") {
            val auctions = mockk<Auctions>() {
                every { getAll() } returns listOf(Auction(Title(title)))
            }
            test("Ok and returns data", module { single { auctions } }) {
                val response = handleGet("/auctions")
                
                response.statusShouldBeOk()
                response.contentAs<AuctionsRto>() shouldBe AuctionsRto(listOf(AuctionRto(title)))
            }
        }
    }
}
