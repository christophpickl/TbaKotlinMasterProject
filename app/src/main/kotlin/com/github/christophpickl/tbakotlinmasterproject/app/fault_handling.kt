package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.Fault
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.InternalFault
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.NotFoundFault
import com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel.ValidationFault
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import mu.KotlinLogging.logger

private val log = logger {}

suspend fun ApplicationCall.respondFault(fault: Fault) {
    log.error { "Faulty respond: $fault" }
    val statusCode = when(fault) {
        is InternalFault -> HttpStatusCode.InternalServerError
        is NotFoundFault -> HttpStatusCode.NotFound
        is ValidationFault -> HttpStatusCode.InternalServerError
    }
    respond(statusCode, ApiErrorRto(message = fault.displayMessage))
}

data class ApiErrorRto(
    val message: String
)
