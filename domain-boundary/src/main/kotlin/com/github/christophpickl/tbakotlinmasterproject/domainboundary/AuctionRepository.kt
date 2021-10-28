package com.github.christophpickl.tbakotlinmasterproject.domainboundary

import arrow.core.Either
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Fault

interface AuctionRepository {
    suspend fun loadAll(): Either<Fault, List<Auction>>
}
