package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import arrow.core.Either
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Fault
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.InternalFault
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun <ReturnType> Database.transactionSafe(errorMessage: String, code: () -> ReturnType): Either<Fault, ReturnType> =
    Either.catch {
        transaction(this) {
            code()
        }
    }.mapDbError(errorMessage)

internal fun <ReturnType> Either<Throwable, ReturnType>.mapDbError(message: String): Either<Fault, ReturnType> =
    mapLeft {
        InternalFault(message, cause = it)
    }
