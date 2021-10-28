package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.commonsktor.Route
import com.github.christophpickl.tbakotlinmasterproject.commonstest.contentAs
import com.github.christophpickl.tbakotlinmasterproject.commonstest.handleGet
import com.github.christophpickl.tbakotlinmasterproject.commonstest.route
import com.github.christophpickl.tbakotlinmasterproject.commonstest.testModule
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.InternalFault
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.NotFoundFault
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.ValidationFault
import com.github.christophpickl.tbakotlinmasterproject.domainmodel.any
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.scopes.DescribeSpecContainerContext
import io.kotest.matchers.shouldBe
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.server.testing.TestApplicationEngine
import org.koin.dsl.bind

class FaultHandlingRouteTest : DescribeSpec() {
    init {
        route("/test/fault") {

            testWithFaultRoute("/InternalFault") {
                val response = handleGet("/test/fault/InternalFault")

                response.status() shouldBe HttpStatusCode.InternalServerError
                response.contentAs<ApiErrorRto>() shouldBe ApiErrorRto(
                    message = TestableFaultHandlingRoute.Faults.internalFault.displayMessage
                )
            }

            testWithFaultRoute("/NotFoundFault") {
                val response = handleGet("/test/fault/NotFoundFault")

                response.status() shouldBe HttpStatusCode.NotFound
                response.contentAs<ApiErrorRto>() shouldBe ApiErrorRto(
                    message = TestableFaultHandlingRoute.Faults.notFoundFault.displayMessage
                )
            }

            testWithFaultRoute("/ValidationFault") {
                val response = handleGet("/test/fault/ValidationFault")

                response.status() shouldBe HttpStatusCode.InternalServerError
                response.contentAs<ApiErrorRto>() shouldBe ApiErrorRto(
                    message = TestableFaultHandlingRoute.Faults.validationFault.displayMessage
                )
            }

        }
    }

    private suspend fun DescribeSpecContainerContext.testWithFaultRoute(
        testName: String,
        testCode: TestApplicationEngine.() -> Unit
    ) {
        test(
            name = testName,
            additionalModules = testModule {
                single { TestableFaultHandlingRoute() } bind Route::class
            },
            testCode = testCode
        )
    }
}

class TestableFaultHandlingRoute : Route {
    object Faults {
        val internalFault = InternalFault.any()
        val notFoundFault = NotFoundFault.any()
        val validationFault = ValidationFault.any()
    }

    override fun Routing.install() {
        route("/test/fault") {
            get("/InternalFault") {
                call.respondFault(Faults.internalFault)
            }
            get("/NotFoundFault") {
                call.respondFault(Faults.notFoundFault)
            }
            get("/ValidationFault") {
                call.respondFault(Faults.validationFault)
            }
        }
    }
}
