package com.github.christophpickl.tbakotlinmasterproject.domainmodel

import arrow.core.ValidatedNel
import arrow.core.validNel
import java.util.UUID

data class Auction(
    val id: AuctionId,
    val title: Title
) {
    companion object
}

@JvmInline
value class AuctionId(val value: UUID) {
    companion object
}

@JvmInline
value class Title private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String): ValidatedNel<InvalidCase, Title> =
            if (value.trim().length != value.length) InvalidCase("Title '$value' must not start/end with whitespace!")
            else if (value.isEmpty()) InvalidCase("Title must not be empty!")
            else Title(value).validNel()
    }
}
