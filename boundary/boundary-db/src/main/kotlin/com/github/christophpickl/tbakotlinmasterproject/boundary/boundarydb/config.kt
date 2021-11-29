package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

data class DatabaseConfig(
    val jdbcUrl: String,
    val driverClassName: String,
    val username: String,
    val password: String
) {
    companion object

    fun connectDatabase(): Database = Database.connect(
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
