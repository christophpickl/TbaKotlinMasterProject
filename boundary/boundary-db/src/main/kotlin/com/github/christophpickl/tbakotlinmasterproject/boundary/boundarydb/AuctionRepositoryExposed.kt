package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import arrow.core.Either
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Fault
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

internal class AuctionRepositoryExposed {

    fun selectAll(db: Database): Either<Fault, List<AuctionDbo>> =
        db.transactionSafe("Select failed!") {
            AuctionTable.selectAll().map {
                AuctionDbo(
                    id = it[AuctionTable.id].value,
                    title = it[AuctionTable.title]
                )
            }
        }

    fun insert(db: Database, dbo: AuctionDbo): Either<Fault, Unit> =
        db.transactionSafe("Insert failed for: $dbo") {
            AuctionTable.insert { row ->
                row[AuctionTable.id] = EntityID(dbo.id, AuctionTable)
                row[title] = dbo.title
            }
        }
}
