package com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel

import arrow.core.NonEmptyList
import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.forced
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.UUID_1

fun Auction.Companion.any() = Auction(
    id = AuctionId.any(),
    title = Title.any(),
)

fun AuctionId.Companion.any() = AuctionId(UUID_1)
fun Title.Companion.any() = Title("any title").forced()

fun Fault.Companion.any() = InternalFault.any()

fun InternalFault.Companion.any() = InternalFault(
    internalMessage = "anyInternalMessage",
    displayMessage = "anyDisplayMessage",
    cause = null
)

fun NotFoundFault.Companion.any() = NotFoundFault(
    internalMessage = "anyInternalMessage",
    displayMessage = "anyDisplayMessage",
    cause = null
)

fun ValidationFault.Companion.any() = ValidationFault(
    invalidCases = NonEmptyList(InvalidCase.any(), emptyList()),
    internalMessage = "anyInternalMessage",
    displayMessage = "anyDisplayMessage"
)

fun InvalidCase.Companion.any() = pure("anyInvalidCase")
