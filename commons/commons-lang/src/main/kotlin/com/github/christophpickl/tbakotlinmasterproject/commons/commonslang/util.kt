package com.github.christophpickl.tbakotlinmasterproject.commons.commonslang

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import java.util.UUID

fun String.toUuid(): Either<CommonFault, UUID> =
    try {
        UUID.fromString(this).right()
    } catch(e: IllegalArgumentException) {
        CommonFault("Invalid UUID: $this", cause = e).left()
    }

inline fun retrieveEnclosingName(noinline func: () -> Unit): String {
    val name = func.javaClass.name
    val slicedName = when {
        name.contains("Kt$") -> name.substringBefore("Kt$")
        name.contains("$") -> name.substringBefore("$")
        else -> name
    }
    return slicedName
}
