package com.github.christophpickl.tbakotlinmasterproject.domain.domainlogic

import arrow.core.right
import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.Modules
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.baseModuleTest
import com.github.christophpickl.tbakotlinmasterproject.domain.domainboundary.AuctionRepository
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auctions
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.any
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.scopes.DescribeSpecContainerContext
import io.kotest.core.test.TestCase
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.coEvery
import io.mockk.mockk
import org.koin.core.Koin
import org.koin.dsl.module

class AuctionServiceModuleTest : DescribeSpec() {

    private val auctions = Auctions.any()
    private lateinit var repository: AuctionRepository

    override fun beforeTest(testCase: TestCase) {
        repository = mockk()
    }

    init {
        describe("load all") {
            moduleTest("Given repository succeds Then return auctions") { koin ->
                coEvery { repository.loadAll() } returns auctions.right()

                val result = koin.get<AuctionService>().loadAll()

                result.shouldBeRight() shouldBeSameInstanceAs auctions
            }
        }
    }

    private suspend fun DescribeSpecContainerContext.moduleTest(testName: String, testCode: suspend (Koin) -> Unit) {
        baseModuleTest(testName, testCode, listOf(Modules.domainLogic(), module { single { repository } }))
    }
}