package com.github.christophpickl.tbakotlinmasterproject.domainlogic

import arrow.core.Either
import arrow.core.computations.either
import com.github.christophpickl.tbakotlinmasterproject.domainboundary.AuctionRepository
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Fault

class AuctionService(
    private val repository: AuctionRepository
) {
    suspend fun loadAll(): Either<Fault, List<Auction>> = either {
        repository.loadAll().bind()
    }
}
