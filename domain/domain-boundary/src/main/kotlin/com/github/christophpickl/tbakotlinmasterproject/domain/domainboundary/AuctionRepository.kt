package com.github.christophpickl.tbakotlinmasterproject.domain.domainboundary

import arrow.core.Either
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auctions
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Fault

interface AuctionRepository {
    suspend fun loadAll(): Either<Fault, Auctions>
    suspend fun insert(auction: Auction): Either<Fault, Unit>
}
