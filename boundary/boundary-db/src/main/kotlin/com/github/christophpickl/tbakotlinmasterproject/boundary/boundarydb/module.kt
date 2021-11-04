package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.Modules
import com.github.christophpickl.tbakotlinmasterproject.domain.domainboundary.AuctionRepository
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.bind
import org.koin.dsl.module

data class DatabaseConfig(
    val url: String,
    val driver: String,
    val username: String,
    val password: String
) {
    companion object

    fun connectDatabase() = Database.connect(
        url = url,
        driver = driver,
        user = username,
        password = password
    )
}

fun Modules.boundaryDb(config: DatabaseConfig) = module {
    single {
        config.connectDatabase()
    }
    single { AuctionRepositoryAdapter(AuctionRepositoryExposed(), get()) } bind AuctionRepository::class
}
