package com.github.christophpickl.tbakotlinmasterproject.domainmodel

sealed class Fault(
    val internalMessage: String,
    val displayMessage: String,
    val cause: Exception? = null
)

class InternalFault(internalMessage: String, displayMessage: String = internalMessage, cause: Exception? = null) : Fault(internalMessage, displayMessage, cause) {
    companion object
}
class NotFoundFault(internalMessage: String, displayMessage: String = internalMessage, cause: Exception? = null) : Fault(internalMessage, displayMessage, cause)
