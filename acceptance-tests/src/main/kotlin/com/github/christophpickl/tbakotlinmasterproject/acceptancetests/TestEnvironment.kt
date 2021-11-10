package com.github.christophpickl.tbakotlinmasterproject.acceptancetests

private const val LOCAL_PORT = 8012

enum class TestEnvironment(
    val baseUrl: String
) {
    Local(
        baseUrl = "http://localhost:$LOCAL_PORT"
    ),
    Development(
        baseUrl = "http://dev.auctions.com"
    ),
    Acceptance(
        baseUrl = "http://acc.auctions.com"
    ),
    Production(
        baseUrl = "http://www.auctions.com"
    );

    companion object {
        val localPort = LOCAL_PORT
        val current = Local // TODO read by env-var and default to local
    }
}
