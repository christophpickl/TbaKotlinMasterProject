package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.Tags
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.UUID_1
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.ResultSet
import java.util.UUID

class AuctionRepositoryExposedDbTest : DescribeSpec() {

    private val tableName = "auctions"
    private val dbo = AuctionDbo.any()
    private val repo = AuctionRepositoryExposed()
    private val uuid = UUID_1
    private lateinit var db: Database

    override fun tags() = setOf(Tags.Database)

    override fun beforeSpec(spec: Spec) {
        db = connectTestDb {}
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        db.deleteAll()
    }

    override fun afterSpec(spec: Spec) {
        TransactionManager.closeAndUnregister(db)
    }

    init {
        describe("When insert") {

            it("Then one row existing") {
                val result = repo.insert(db, AuctionDbo.any())

                result.shouldBeRight()
                val rs = db.query("SELECT * FROM $tableName")
                rs.countRows() shouldBe 1
            }

            it("Then values inserted") {
                val result = repo.insert(db, dbo)

                result.shouldBeRight()
                val rs = db.query("SELECT * FROM $tableName")
                rs.next()
                rs.toAuctionDbo() shouldBe dbo
            }

            it("Given auction with same ID Then fault") {
                insert(AuctionDbo.any().copy(id = uuid))

                val result = repo.insert(db, AuctionDbo.any().copy(id = uuid))

                result.shouldBeLeft()
            }
        }
        
        describe("When select all") {

            it("Then return empty") {
                val result = repo.selectAll(db)

                result.shouldBeRight().shouldBeEmpty()
            }

            it("Given inserted auction Then return it") {
                insert(dbo)

                val result = repo.selectAll(db)

                result.shouldBeRight().shouldContainExactly(dbo)
            }
        }
    }

    private fun insert(dbo: AuctionDbo) {
        db.execute("INSERT INTO $tableName (id, title) VALUES ('${dbo.id}', '${dbo.title}')")
    }

    private fun ResultSet.toAuctionDbo() = AuctionDbo(
        id = UUID.fromString(getString("id")),
        title = getString("title")
    )
}