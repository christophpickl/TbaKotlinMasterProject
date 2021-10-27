package com.github.christophpickl.tbakotlinmasterproject.domainlogic

import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Auction
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Title

class Auctions {
    fun getAll(): List<Auction> {
        return listOf(Auction(Title("dummy auction")))
    }
}
