package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import arrow.core.Either
import arrow.core.computations.either
import com.github.christophpickl.tbakotlinmasterproject.domain.domainboundary.AuctionRepository
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.AuctionId
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Fault
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Title
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.ValidationFault
import java.util.UUID

internal class AuctionRepositoryAdapter(
    private val auctionRepositoryExposed: AuctionRepositoryExposed
) : AuctionRepository {
    override suspend fun loadAll(): Either<Fault, List<Auction>> = either {
        auctionRepositoryExposed.selectAll().map { dbos ->
            dbos.map { dbo ->
                dbo.toAuction().bind()
            }
        }.bind()
    }

    private suspend fun AuctionDbo.toAuction(): Either<Fault, Auction> = either {
        Auction(
            id = AuctionId(id.toUuid().bind()),
            title = Title(title).mapLeft {
                ValidationFault(it, "Title from DB is invalid for auction with ID: '$id'!")
            }.bind(),
        )
    }
}

internal class AuctionRepositoryExposed {
    suspend fun selectAll(): Either<Fault, List<AuctionDbo>> = either {
        listOf(AuctionDbo(UUID.randomUUID().toString(), "title"))
    }
}
