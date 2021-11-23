package com.github.christophpickl.tbakotlinmasterproject.acceptancetests

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
    private val port: Int
) {

    private val log = logger {}
    private lateinit var ktor: NettyApplicationEngine
    private lateinit var ktorJob: Job
    private lateinit var postgres: PostgreSQLContainer<Nothing>

    suspend fun startInfra() {
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

    suspend fun resetData() {
        AcceptanceTestRoute.requestDeleteAll()
    }

    suspend fun stopInfra() {
        log.info { "Stopping Ktor server." }
        ktor.stop(2_000L, 5_000L)
        ktorJob.join()

        log.info { "Stopping PostgreSQL testcontainer." }
        postgres.stop()
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

    private fun testModule() = module {
        single { AcceptanceTestRoute(get()) } bind Route::class
    }
}
