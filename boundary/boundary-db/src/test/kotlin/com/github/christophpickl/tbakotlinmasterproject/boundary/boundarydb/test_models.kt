package com.github.christophpickl.tbakotlinmasterproject.boundary.boundarydb

import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.UUID_ANY

fun AuctionDbo.Companion.any() = AuctionDbo(
    id = UUID_ANY,
    title = "anyTitle"
)

fun DatabaseConfig.Companion.any() = DatabaseConfig(
    jdbcUrl = "anyJdbcUrl",
    driverClassName = "anyDriverClassName",
    username = "anyUsername",
    password = "anyPassword"
)
