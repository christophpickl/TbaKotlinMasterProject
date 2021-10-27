package com.github.christophpickl.tbakotlinmasterproject.domainmodel

data class Auction(
    val title: Title
) {
    companion object
}

@JvmInline
value class Title(val value: String) {
    companion object
}
