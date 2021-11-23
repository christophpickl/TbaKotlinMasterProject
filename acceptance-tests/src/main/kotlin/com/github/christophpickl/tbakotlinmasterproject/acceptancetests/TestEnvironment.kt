package com.github.christophpickl.tbakotlinmasterproject.acceptancetests

import com.github.christophpickl.tbakotlinmasterproject.commons.commonslang.readRuntimeVariable

private const val LOCAL_PORT = 8012

enum class TestEnvironment(
    val id: String,
    val baseUrl: String
) {
    Local(
        id = "loc",
        baseUrl = "http://localhost:$LOCAL_PORT"
    ),
    Development(
        id = "dev",
        baseUrl = "http://dev.auctions.com"
    ),
    Acceptance(
        id = "acc",
        baseUrl = "http://acc.auctions.com"
    ),
    Production(
        id = "prd",
        baseUrl = "http://www.auctions.com"
    );

    companion object {
        const val localPort = LOCAL_PORT
        private const val SYSTEM_OR_ENV_KEY = "testEnv"

        val current by lazy {
            testEnvironmentById(readRuntimeVariable(SYSTEM_OR_ENV_KEY, Local.id))
        }

        private val environmentsById: Map<String, TestEnvironment> by lazy {
            values().associateBy { it.id }
        }

        private fun testEnvironmentById(id: String) = environmentsById[id]
            ?: throw IllegalArgumentException("Invalid test environment ID: '$id' (available: ${values().joinToString { it.id }}")
    }
}
