package com.github.christophpickl.tbakotlinmasterproject.acceptance.tests

import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.HttpRequester
import com.github.christophpickl.tbakotlinmasterproject.commons.commonsktor.KtorHttpRequester

object ServiceClient {
    operator fun invoke(): HttpRequester =
        ServiceClientImpl(KtorHttpRequester(TestEnvironment.current.baseUrl))
}

class ServiceClientImpl(
    client: KtorHttpRequester
): HttpRequester by client
