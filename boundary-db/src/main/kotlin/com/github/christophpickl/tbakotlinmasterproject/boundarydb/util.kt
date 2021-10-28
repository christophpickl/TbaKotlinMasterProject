package com.github.christophpickl.tbakotlinmasterproject.boundarydb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.Fault
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.InternalFault
import java.util.UUID

internal fun UuidString.toUuid(): Either<Fault, UUID> =
    try {
        UUID.fromString(this).right()
    } catch(e: IllegalArgumentException) {
        InternalFault("Invalid UUID: $this", cause = e).left()
    }
