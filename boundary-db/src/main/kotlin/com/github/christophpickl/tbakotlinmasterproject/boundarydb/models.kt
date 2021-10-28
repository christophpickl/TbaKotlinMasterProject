package com.github.christophpickl.tbakotlinmasterproject.boundarydb

internal typealias UuidString = String

internal data class AuctionDbo(
    val id: UuidString,
    val title: String,
)
