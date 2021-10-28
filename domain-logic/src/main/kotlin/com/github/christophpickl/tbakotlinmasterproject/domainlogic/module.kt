package com.github.christophpickl.tbakotlinmasterproject.domainlogic

import com.github.christophpickl.tbakotlinmasterproject.commonstest.Modules
import org.koin.dsl.module

fun Modules.domainLogic() = module {
    single { AuctionService(get()) }
}
