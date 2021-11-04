package com.github.christophpickl.tbakotlinmasterproject.domain.domainlogic

import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.Modules
import org.koin.dsl.module

fun Modules.domainLogic() = module {
    single { AuctionService(get()) }
}
