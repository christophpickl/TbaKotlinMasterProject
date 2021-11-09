package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.Modules
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.baseModuleTest
import com.github.christophpickl.tbakotlinmasterproject.domain.domainboundary.AuctionRepository
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.any
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.scopes.DescribeSpecContainerContext
import org.jetbrains.exposed.sql.Database
import org.koin.core.Koin

class AuctionRepositoryModuleTest : DescribeSpec() {
    init {
        describe("Given boundary-db module") {

            moduleTest("When load all auctions Then succeed") { koin ->
                val result = koin.get<AuctionRepository>().insert(Auction.any())

                result.shouldBeRight()
            }

            moduleTest("When insert auction Then succeed") { koin ->
                val result = koin.get<AuctionRepository>().insert(Auction.any())

                result.shouldBeRight()
            }
        }
    }

    private suspend fun DescribeSpecContainerContext.moduleTest(testName: String, testCode: suspend (Koin) -> Unit) {
        baseModuleTest(testName, testCode, listOf(Modules.boundaryDb(DatabaseConfig.testConfig {})) ) { koin ->
            koin.get<Database>().createAllTables()
        }
    }
}
