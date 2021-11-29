package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.retrieveEnclosingName
import mu.KotlinLogging.logger
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.ResultSet
import java.util.concurrent.atomic.AtomicLong

private val allTables = listOf<Table>(AuctionTable)
private val dbCounter = AtomicLong(1L)

private val log = logger {}

fun DatabaseConfig.Companion.testConfig(nameRetriever: () -> Unit): DatabaseConfig {
    val testClassName = retrieveEnclosingName(nameRetriever)
    return DatabaseConfig(
        jdbcUrl = "jdbc:h2:mem:${testClassName}_${dbCounter.getAndIncrement()};DB_CLOSE_DELAY=-1;",
        driverClassName = "org.h2.Driver",
        username = "",
        password = ""
    )
}

fun connectTestDb(nameRetriever: () -> Unit): Database {
    val db = DatabaseConfig.testConfig(nameRetriever).connectDatabase()
    db.createAllTables()
    return db
}

fun Database.createAllTables() {
    log.info { "createAllTables" }
    transaction(this) {
        SchemaUtils.create(*allTables.toTypedArray())
    }
}

fun Database.deleteAll() {
    log.debug { "deleteAll" }
    transaction(this) {
        allTables.forEach {
            it.deleteAll()
        }
    }
}

fun Database.query(sql: String): ResultSet {
    val stmt = connector().createStatement()
    return stmt.executeQuery(sql)
}

fun Database.execute(sql: String): Int {
    val connection = connector()
    val stmt = connection.createStatement()
    try {
        val result = stmt.executeUpdate(sql)
        connection.commit()
        return result
    } catch (e: Exception) {
        connection.rollback()
        throw e
    }
}

fun ResultSet.countRows(): Int {
    var count = 0
    while (next()) {
        count++
    }
    return count
}
