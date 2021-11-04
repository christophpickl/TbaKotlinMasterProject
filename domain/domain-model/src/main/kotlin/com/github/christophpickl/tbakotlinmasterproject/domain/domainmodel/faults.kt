package com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel

import arrow.core.NonEmptyList
import arrow.core.invalidNel

const val DEFAULT_DISPLAY_MESSAGE = "Unknown error."
// TODO change to interface
sealed class Fault(
    val internalMessage: String,
    val displayMessage: String,
    val cause: Throwable? = null
) {
    companion object
}

class InternalFault(internalMessage: String, displayMessage: String = DEFAULT_DISPLAY_MESSAGE, cause: Throwable? = null) :
    Fault(internalMessage, displayMessage, cause) {
    companion object
}

class NotFoundFault(internalMessage: String, displayMessage: String = DEFAULT_DISPLAY_MESSAGE, cause: Throwable? = null) :
    Fault(internalMessage, displayMessage, cause) {
    companion object
}

class InvalidCase private constructor(val message: String) {
    companion object {
        operator fun invoke(message: String) = InvalidCase(message).invalidNel()
        fun pure(message: String) = InvalidCase(message)
    }
}

class ValidationFault(val invalidCases: NonEmptyList<InvalidCase>, internalMessage: String, displayMessage: String = internalMessage) :
    Fault(internalMessage, displayMessage, cause = null) {
    companion object
}
