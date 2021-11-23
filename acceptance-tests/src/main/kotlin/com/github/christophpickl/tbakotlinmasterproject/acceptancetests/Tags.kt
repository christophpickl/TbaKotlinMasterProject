package com.github.christophpickl.tbakotlinmasterproject.acceptancetests

import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.Tags
import io.kotest.core.NamedTag

private val requiresPostgresTestcontainers = NamedTag("RequiresPostgresTestcontainers")
val Tags.RequiresPostgresTestcontainers get() = requiresPostgresTestcontainers

private val readOnly = NamedTag("ReadOnly")
val Tags.ReadOnly get() = readOnly

private val write = NamedTag("Write")
val Tags.Write get() = write

private val testEndpoint = NamedTag("TestEndpoint")
val Tags.TestEndpoint get() = testEndpoint