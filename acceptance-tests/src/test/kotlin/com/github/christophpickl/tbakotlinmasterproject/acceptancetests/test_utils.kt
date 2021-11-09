package com.github.christophpickl.tbakotlinmasterproject.acceptancetests

import com.github.christophpickl.tbakotlinmasterproject.app.AppConfig
import com.github.christophpickl.tbakotlinmasterproject.app.configureKtor
import com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb.DatabaseConfig
import com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb.createAllTables
import com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb.deleteAll
import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.Route
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.Tags
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.kotest.LazyTestPreparer
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.listeners.ProjectListener
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import io.ktor.application.ApplicationStarted
import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mu.KotlinLogging.logger
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.bind
import org.koin.dsl.module
import org.testcontainers.containers.PostgreSQLContainer
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

private val log = logger {}

class KtorAppStarter(
    private val port: Int
) : ProjectListener, BeforeTestListener {

    private val log = logger {}
    private lateinit var ktor: NettyApplicationEngine
    private lateinit var ktorJob: Job
    private lateinit var postgres: PostgreSQLContainer<Nothing>

    override suspend fun beforeProject() {
        postgres = startPostgres()
        val config = AppConfig(
            port = port,
            dbConfig = DatabaseConfig(
                jdbcUrl = postgres.jdbcUrl,
                driverClassName = postgres.driverClassName,
                username = postgres.username,
                password = postgres.password
            )
        )
        startKtor(config)
        delay(500) // wait a bit
        AcceptanceTestRoute.requestCreateTables()
    }

    override suspend fun beforeTest(testCase: TestCase) {
        AcceptanceTestRoute.requestDeleteAll()
    }

    private fun startPostgres(): PostgreSQLContainer<Nothing> {
        val postgres = PostgreSQLContainer<Nothing>("postgres:9.6.12")
        log.info { "Starting PostgreSQL in testcontainer." }
        postgres.start()
        return postgres
    }

    private fun startKtor(config: AppConfig) {
        val signal: BlockingQueue<Any> = LinkedBlockingQueue()
        ktor = embeddedServer(Netty, port = config.port) {
            configureKtor(config, additionalModules = listOf(testModule()))
            environment.monitor.subscribe(ApplicationStarted) {
                log.info { "Ktor application started." }
                signal.add(true)
            }
        }
        log.info { "Starting Ktor server on port: ${config.port}" }
        ktorJob = GlobalScope.launch {
            ktor.start(wait = true)
        }
        signal.take()
    }

    override suspend fun afterProject() {
        log.info { "Stopping Ktor server." }
        ktor.stop(2_000L, 5_000L)
        ktorJob.join()

        log.info { "Stopping PostgreSQL testcontainer." }
        postgres.stop()
    }
}

private fun testModule() = module {
    single { AcceptanceTestRoute(get()) } bind Route::class
}

class AcceptanceTestRoute(
    private val db: Database
) : Route {
    companion object {
        suspend fun requestCreateTables() {
            request(HttpMethod.Get, "/acceptanceTest/createTables").status shouldBe HttpStatusCode.OK
        }
        suspend fun requestDeleteAll() {
            request(HttpMethod.Get, "/acceptanceTest/deleteAll").status shouldBe HttpStatusCode.OK
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

object DatabaseMigrationListener : LazyTestPreparer(
    tags = setOf(Tags.Database),
    lazySetup = {
        // FIXME TestContainer
    },
    tearDown = {
    }
)

suspend fun request(method: HttpMethod, path: String): HttpResponse =
    HttpClient(CIO) {
        expectSuccess = false
    }.use {
        val fullUrl = "${TestEnvironment.current.baseUrl}$path"
        log.debug { "Requesting: ${method.value} $fullUrl" }
        it.request(fullUrl) {
            this.method = method
        }
    }
