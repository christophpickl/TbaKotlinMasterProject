@file:JvmName("Util")
package com.github.christophpickl.tbakotlinmasterproject.commons.commonslang

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import mu.KotlinLogging.logger
import java.util.UUID

private val log = logger {}

fun String.toUuid(): Either<CommonFault, UUID> =
    try {
        UUID.fromString(this).right()
    } catch (e: IllegalArgumentException) {
        CommonFault("Invalid UUID: $this", cause = e).left()
    }

@Suppress("NOTHING_TO_INLINE")
inline fun retrieveEnclosingName(noinline func: () -> Unit): String {
    val name = func.javaClass.name
    val slicedName = when {
        name.contains("Kt$") -> name.substringBefore("Kt$")
        name.contains("$") -> name.substringBefore("$")
        else -> name
    }
    return slicedName
}

fun readRuntimeVariableOrNull(key: String): String? {
        log.debug { "Reading runtime variable '$key'." }
        return System.getProperty(key) ?: System.getenv(key)
}

fun readRuntimeVariable(key: String, default: String): String =
    readRuntimeVariableOrNull(key) ?: default

fun readRuntimeVariableOrThrow(key: String): String =
    readRuntimeVariableOrNull(key) ?: throw IllegalStateException("Mandatory system/environment variable '$key' was not set!")
