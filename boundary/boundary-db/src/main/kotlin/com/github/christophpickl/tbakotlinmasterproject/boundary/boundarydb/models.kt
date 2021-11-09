package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import java.util.UUID

data class AuctionDbo(
    val id: UUID,
    val title: String,
) {
    companion object {
        const val TITLE_MAXLENGTH = 255
    }
}
