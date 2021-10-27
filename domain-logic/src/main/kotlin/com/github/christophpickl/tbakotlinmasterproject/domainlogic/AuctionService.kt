package com.github.christophpickl.tbakotlinmasterproject.domainlogic

import arrow.core.Either
import arrow.core.computations.either
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Fault
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Title

class AuctionService {
    suspend fun getAll(): Either<Fault, List<Auction>> = either {
        listOf(Auction(Title("dummy auction")))
    }
}
