package com.github.christophpickl.tbakotlinmasterproject.domain.domainlogic

import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.Modules
import org.koin.dsl.module

@Suppress("unused")
fun Modules.domainLogic() = module {
    single { AuctionService(get()) }
}
