package com.github.christophpickl.tbakotlinmasterproject.commons.commonstest

import io.kotest.core.spec.style.scopes.DescribeSpecContainerContext
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

suspend fun DescribeSpecContainerContext.baseModuleTest(
    testName: String,
    testCode: suspend (Koin) -> Unit,
    modules: List<Module>,
    beforeTest: (Koin) -> Unit = {}
) {
    it(testName) {
        val koin = startKoin {
            modules(modules)
        }.koin
        beforeTest(koin)
        try {
            testCode(koin)
        } finally {
            stopKoin()
        }
    }
}
