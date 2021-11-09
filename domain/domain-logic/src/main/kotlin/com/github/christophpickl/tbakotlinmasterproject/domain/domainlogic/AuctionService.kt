package com.github.christophpickl.tbakotlinmasterproject.domain.domainlogic

import arrow.core.Either
import arrow.core.computations.either
import com.github.christophpickl.tbakotlinmasterproject.domain.domainboundary.AuctionRepository
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Auctions
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Fault

class AuctionService(
    private val repository: AuctionRepository
) {
    suspend fun loadAll(): Either<Fault, Auctions> = either {
        repository.loadAll().bind()
    }
}
