package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.Modules
import com.github.christophpickl.tbakotlinmasterproject.domain.domainboundary.AuctionRepository
import org.koin.dsl.bind
import org.koin.dsl.module

@Suppress("unused")
fun Modules.boundaryDb(config: DatabaseConfig) = module {
    single {
        config.connectDatabase()
    }
    single { AuctionRepositoryAdapter(AuctionRepositoryExposed(), get()) } bind AuctionRepository::class
}
