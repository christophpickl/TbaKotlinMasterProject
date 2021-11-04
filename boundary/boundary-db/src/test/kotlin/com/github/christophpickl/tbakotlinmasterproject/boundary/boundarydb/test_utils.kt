package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.retrieveEnclosingName
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.ResultSet
import kotlin.random.Random

private val allTables = listOf<Table>(AuctionTable)

fun DatabaseConfig.Companion.testConfig(nameRetriever: () -> Unit): DatabaseConfig {
    val testClassName = retrieveEnclosingName(nameRetriever)
    return DatabaseConfig(
        url = "jdbc:h2:mem:${testClassName}_${Random.nextInt()};DB_CLOSE_DELAY=-1;",
        driver = "org.h2.Driver",
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
    transaction(this) {
        SchemaUtils.create(*allTables.toTypedArray())
    }
}

fun Database.deleteAll() {
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
    val stmt = connector().createStatement()
    return stmt.executeUpdate(sql)
}

fun ResultSet.countRows(): Int {
    var count = 0
    while(next()) {
        count++
    }
    return count
}
