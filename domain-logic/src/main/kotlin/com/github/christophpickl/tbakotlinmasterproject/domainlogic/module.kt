package com.github.christophpickl.tbakotlinmasterproject.domainlogic

import org.koin.dsl.module

fun domainLogicModule() = module {
    single { AuctionService() }
}
