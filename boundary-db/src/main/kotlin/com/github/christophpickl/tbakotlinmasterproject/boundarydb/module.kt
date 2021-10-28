package com.github.christophpickl.tbakotlinmasterproject.boundarydb

import com.github.christophpickl.tbakotlinmasterproject.commonstest.Modules
import com.github.christophpickl.tbakotlinmasterproject.domainboundary.AuctionRepository
import org.koin.dsl.bind
import org.koin.dsl.module

fun Modules.boundaryDb() = module {
    single { AuctionRepositoryAdapter(AuctionRepositoryExposed()) } bind AuctionRepository::class
}
