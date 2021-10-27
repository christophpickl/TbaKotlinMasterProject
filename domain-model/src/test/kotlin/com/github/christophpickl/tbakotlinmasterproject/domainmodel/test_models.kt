package com.github.christophpickl.tbakotlinmasterproject.domainmodel

fun Auction.Companion.any() = Auction(
    title = Title.any()
)

fun Title.Companion.any() = Title("any title")

fun InternalFault.Companion.any() = InternalFault(
    internalMessage = "anyInternalMessage",
    displayMessage = "anyDisplayMessage",
    cause = null
)