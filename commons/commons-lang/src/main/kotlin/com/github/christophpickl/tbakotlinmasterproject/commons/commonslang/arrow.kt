package com.github.christophpickl.tbakotlinmasterproject.commons.commonslang

import arrow.core.Validated
import arrow.core.orNull

fun <E, A> Validated<E, A>.forced(): A =
    orNull() ?: throw IllegalStateException("Tried to force an invalid entity: $this")

data class CommonFault(
    val message: String,
    val cause: Exception? = null
)
