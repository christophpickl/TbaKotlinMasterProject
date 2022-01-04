package com.github.christophpickl.tbakotlinmasterproject.acceptance.tests

import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.Tags
import io.kotest.core.NamedTag

private val requiresPostgresTestcontainers = NamedTag("RequiresPostgresTestcontainers")
@Suppress("unused")
val Tags.RequiresPostgresTestcontainers get() = requiresPostgresTestcontainers

private val readOnly = NamedTag("ReadOnly")
@Suppress("unused")
val Tags.ReadOnly get() = readOnly

private val write = NamedTag("Write")
@Suppress("unused")
val Tags.Write get() = write

private val testEndpoint = NamedTag("TestEndpoint")
@Suppress("unused")
val Tags.TestEndpoint get() = testEndpoint