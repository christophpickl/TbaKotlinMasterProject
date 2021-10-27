package com.github.christophpickl.tbakotlinmasterproject.apimodel

/** Marker interface. */
interface Rto

data class AuctionsRto(
    val auctions: List<AuctionRto>
): Rto

data class AuctionRto(
    val title: String
): Rto
