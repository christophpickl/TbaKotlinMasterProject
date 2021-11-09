package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.Modules
import com.github.christophpickl.tbakotlinmasterproject.domain.domainboundary.AuctionRepository
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.bind
import org.koin.dsl.module

data class DatabaseConfig(
    val jdbcUrl: String,
    val driverClassName: String,
    val username: String,
    val password: String
) {
    companion object

    fun connectDatabase(): Database =
        Database.connect(
            datasource = HikariDataSource(HikariConfig().also {
                it.jdbcUrl = jdbcUrl
                it.username = username
                it.password = password
                it.driverClassName = driverClassName
                it.maximumPoolSize = 10
                it.connectionTimeout = 10_000L
                it.isAutoCommit = false
                it.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            })
        )
}

fun Modules.boundaryDb(config: DatabaseConfig) = module {
    single {
        config.connectDatabase()
    }
    single { AuctionRepositoryAdapter(AuctionRepositoryExposed(), get()) } bind AuctionRepository::class
}
