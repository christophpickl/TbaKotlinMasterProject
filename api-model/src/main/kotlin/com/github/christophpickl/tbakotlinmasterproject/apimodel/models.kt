package com.github.christophpickl.tbakotlinmasterproject.apimodel

data class AuctionsRto(
    val auctions: List<AuctionRto>
)

data class AuctionRto(
    val title: String
)
