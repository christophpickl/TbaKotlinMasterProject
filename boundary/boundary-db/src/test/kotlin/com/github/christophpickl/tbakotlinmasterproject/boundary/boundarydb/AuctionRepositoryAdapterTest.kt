package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import arrow.core.left
import arrow.core.right
import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.forced
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.AuctionId
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Fault
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Title
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.any
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jetbrains.exposed.sql.Database

internal class AuctionRepositoryAdapterTest : DescribeSpec() {

    private val fault = Fault.any()
    private val auction = Auction.any()
    private val auctionDbo = AuctionDbo.any()
    private lateinit var exposed: AuctionRepositoryExposed
    private lateinit var database: Database
    private lateinit var adapter: AuctionRepositoryAdapter

    override fun beforeTest(testCase: TestCase) {
        exposed = mockk()
        database = mockk()
        adapter = AuctionRepositoryAdapter(exposed, database)
    }

    init {
        describe("When loadAll") {

            it("Given right exposed repo Then transform and return") {
                every { exposed.selectAll(any()) } returns listOf(auctionDbo).right()

                val result = adapter.loadAll()

                result.shouldBeRight().shouldBeSingleton().first() shouldBe Auction(
                    id = AuctionId(value = auctionDbo.id),
                    title = Title(value = auctionDbo.title).forced()
                )
            }

            it("Given faulty exposed repo Then also fail") {
                every { exposed.selectAll(any()) } returns fault.left()

                val result = adapter.loadAll()

                result.shouldBeLeft() shouldBeSameInstanceAs fault
            }
        }

        describe("When insert") {

            it("Given right exposed repo Then inserted") {
                every { exposed.insert(any(), any()) } returns Unit.right()

                val result = adapter.insert(auction)

                result.shouldBeRight()
                verify { exposed.insert(database, auction.toAuctionDbo()) }
            }

            it("Given faulty exposed repo Then also fail") {
                every { exposed.insert(any(), any()) } returns fault.left()

                val result = adapter.insert(Auction.any())

                result.shouldBeLeft() shouldBeSameInstanceAs fault
            }
        }

    }

    private fun Auction.toAuctionDbo() = AuctionDbo(
        id = id.value,
        title = title.value
    )
}