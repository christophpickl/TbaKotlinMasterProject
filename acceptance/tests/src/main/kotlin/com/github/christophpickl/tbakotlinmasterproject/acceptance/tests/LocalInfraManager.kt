package com.github.christophpickl.tbakotlinmasterproject.acceptance.tests

import com.github.christophpickl.tbakotlinmasterproject.acceptance.routes.AcceptanceTestRoute
import com.github.christophpickl.tbakotlinmasterproject.app.AppConfig
import com.github.christophpickl.tbakotlinmasterproject.app.configureKtor
import com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb.DatabaseConfig
import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.Route
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.Tags
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.kotest.LazyTestPreparer
import io.ktor.application.ApplicationStarted
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mu.KotlinLogging.logger
import org.koin.dsl.bind
import org.koin.dsl.module
import org.testcontainers.containers.PostgreSQLContainer
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class LocalInfraListener(
    private val manager: LocalInfraManager
) : LazyTestPreparer(
    tags = setOf(Tags.RequiresPostgresTestcontainers),
    lazyBeforeSpecPreparation = {
        manager.startInfra()
    },
    lazyBeforeTestPreparation = {
        manager.resetData()
    },
    lazyAfterProjectPostperation = {
        manager.stopInfra()
    }
)

class LocalInfraManager(
    private val ktorPort: Int,
    private val acceptanceRoutesClient: AcceptanceRoutesClient
) {
    companion object {
        private const val START_INFRA_DELAY_IN_MS = 500L
        private const val STOP_INFRA_GRACE_IN_MS = 2_000L
        private const val STOP_INFRA_TIMEOUT_IN_MS = 5_000L
    }
    private val log = logger {}
    private lateinit var ktor: NettyApplicationEngine
    private lateinit var ktorJob: Job
    private lateinit var postgres: PostgreSQLContainer<Nothing>

    suspend fun startInfra() {
        postgres = startPostgres()
        startKtor(AppConfig(
            port = ktorPort,
            dbConfig = DatabaseConfig(
                jdbcUrl = postgres.jdbcUrl,
                driverClassName = postgres.driverClassName,
                username = postgres.username,
                password = postgres.password
            )
        ))
        delay(START_INFRA_DELAY_IN_MS) // wait a bit
        acceptanceRoutesClient.requestCreateTables()
    }

    suspend fun resetData() {
        acceptanceRoutesClient.requestDeleteAll()
    }

    suspend fun stopInfra() {
        log.info { "Stopping Ktor server." }
        ktor.stop(STOP_INFRA_GRACE_IN_MS, STOP_INFRA_TIMEOUT_IN_MS)
        ktorJob.join()

        log.info { "Stopping PostgreSQL testcontainer." }
        postgres.stop()
    }

    private fun startPostgres(): PostgreSQLContainer<Nothing> {
        val postgres = PostgreSQLContainer<Nothing>("postgres:9.6.12")
        log.info { "Starting PostgreSQL in testcontainer." }
        try {
            postgres.start()
        } catch(e: IllegalStateException) {
            if(e.message?.contains("Could not find a valid Docker environment") == true) {
                throw IllegalStateException("Please start Docker runtime.", e)
            }
            throw e
        }
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

    private fun testModule() = module {
        single { AcceptanceTestRoute(get()) } bind Route::class
    }
}
