package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import org.jetbrains.exposed.dao.UUIDTable

internal object AuctionTable : UUIDTable("auctions", columnName = "id") {
    val title = varchar("title", AuctionDbo.TITLE_MAXLENGTH)
}
