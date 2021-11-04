package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.Modules
import com.github.christophpickl.tbakotlinmasterproject.domain.domainboundary.AuctionRepository
import org.koin.dsl.bind
import org.koin.dsl.module

fun Modules.boundaryDb() = module {
    single { AuctionRepositoryAdapter(AuctionRepositoryExposed()) } bind AuctionRepository::class
}
