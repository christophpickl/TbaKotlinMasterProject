package com.github.christophpickl.tbakotlinmasterproject.commons.commonstest

import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module

fun testModule(moduleDeclaration: ModuleDeclaration) =
    listOf(module(createdAtStart = true, moduleDeclaration = moduleDeclaration))
