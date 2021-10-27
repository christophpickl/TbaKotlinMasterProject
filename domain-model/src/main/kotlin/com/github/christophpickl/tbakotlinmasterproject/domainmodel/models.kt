package com.github.christophpickl.tbakotlinmasterproject.domainmodel

data class Auction(
    val title: Title
)

@JvmInline
value class Title(val value: String)
