package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import arrow.core.Either
import arrow.core.computations.either
import com.github.christophpickl.tbakotlinmasterproject.domain.domainboundary.AuctionRepository
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.AuctionId
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Fault
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Title
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.ValidationFault
import org.jetbrains.exposed.sql.Database

internal class AuctionRepositoryAdapter(
    private val auctionRepositoryExposed: AuctionRepositoryExposed,
    private val database: Database
) : AuctionRepository {

    override suspend fun loadAll(): Either<Fault, List<Auction>> = either {
        auctionRepositoryExposed.selectAll(database).map { dbos ->
            dbos.map { dbo ->
                dbo.toAuction().bind()
            }
        }.bind()
    }

    override suspend fun insert(auction: Auction): Either<Fault, Unit> = either {
        auctionRepositoryExposed.insert(database, auction.toAuctionDbo()).bind()
    }
}

private suspend fun AuctionDbo.toAuction(): Either<Fault, Auction> = either {
    Auction(
        id = AuctionId(id),
        title = Title(title).mapLeft {
            ValidationFault(it, "Title from DB is invalid for auction with ID: '$id'!")
        }.bind(),
    )
}

private fun Auction.toAuctionDbo() = AuctionDbo(
    id = id.value,
    title = title.value
)
