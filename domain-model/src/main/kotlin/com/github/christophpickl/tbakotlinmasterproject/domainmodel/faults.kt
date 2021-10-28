package com.github.christophpickl.tbakotlinmasterproject.domainmodel

import arrow.core.NonEmptyList
import arrow.core.invalidNel

sealed class Fault(
    val internalMessage: String,
    val displayMessage: String,
    val cause: Exception? = null
)

class InternalFault(internalMessage: String, displayMessage: String = internalMessage, cause: Exception? = null) :
    Fault(internalMessage, displayMessage, cause) {
    companion object
}

class NotFoundFault(internalMessage: String, displayMessage: String = internalMessage, cause: Exception? = null) :
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
