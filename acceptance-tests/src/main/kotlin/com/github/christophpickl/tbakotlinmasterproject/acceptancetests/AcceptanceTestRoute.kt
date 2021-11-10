package com.github.christophpickl.tbakotlinmasterproject.acceptancetests

import com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb.createAllTables
import com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb.deleteAll
import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.Route
import io.kotest.matchers.shouldBe
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import org.jetbrains.exposed.sql.Database

class AcceptanceTestRoute(
    private val db: Database
) : Route {

    companion object {
        suspend fun requestCreateTables() {
            Http.GET("/acceptanceTest/createTables").status shouldBe HttpStatusCode.OK
        }

        suspend fun requestDeleteAll() {
            Http.GET("/acceptanceTest/deleteAll").status shouldBe HttpStatusCode.OK
        }
    }

    override fun Routing.install() {
        route("/acceptanceTest") {
            get("/createTables") {
                db.createAllTables()
                call.respondText("DB tables created")
            }
            get("/deleteAll") {
                db.deleteAll()
                call.respondText("DB content deleted")
            }
        }
    }
}
