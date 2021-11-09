package com.github.christophpickl.tbakotlinmasterproject.domain.domainlogic

import arrow.core.left
import arrow.core.right
import com.github.christophpickl.tbakotlinmasterproject.domain.domainboundary.AuctionRepository
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auctions
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Fault
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.any
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.coEvery
import io.mockk.mockk

class AuctionServiceTest : DescribeSpec() {

    private val fault = Fault.any()
    private val auctions = Auctions.any()
    private lateinit var repository: AuctionRepository
    private lateinit var service: AuctionService

    override fun beforeTest(testCase: TestCase) {
        repository = mockk()
        service = AuctionService(repository)
    }

    init {
        describe("load all") {

            it("Given repo faults Then return fault") {
                coEvery { repository.loadAll() } returns fault.left()

                val result = service.loadAll()

                result.shouldBeLeft() shouldBeSameInstanceAs fault
            }

            it("Given repo succeeds Then return auctions") {
                coEvery { repository.loadAll() } returns auctions.right()

                val result = service.loadAll()

                result.shouldBeRight() shouldBeSameInstanceAs auctions
            }
        }
    }
}