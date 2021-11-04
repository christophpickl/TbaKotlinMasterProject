package com.github.christophpickl.tbakotlinmasterproject.domain.domainlogic

import arrow.core.Either
import arrow.core.computations.either
import com.github.christophpickl.tbakotlinmasterproject.domain.domainboundary.AuctionRepository
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Fault

class AuctionService(
    private val repository: AuctionRepository
) {
    suspend fun loadAll(): Either<Fault, List<Auction>> = either {
        repository.loadAll().bind()
    }
}
// TODO test this
