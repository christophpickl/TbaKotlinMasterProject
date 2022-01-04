package com.github.christophpickl.tbakotlinmasterproject.acceptance.tests

import io.kotest.core.listeners.TestListener
import io.kotest.core.test.TestCase

class GherkinStateManager : GherkinInterpreter, TestListener {

    private var instance = GherkinInstance()

    operator fun invoke(code: GivenActions.() -> Unit)  = Given(code)

    override suspend fun beforeTest(testCase: TestCase) {
        println("reset gherkin state")
        instance = GherkinInstance()
    }

    override fun Given(code: GivenActions.() -> Unit) = instance.Given(code)
    override fun When(code: WhenActions.() -> Unit) = instance.When(code)
    override fun Then(code: ThenActions.() -> Unit) = instance.Then(code)
    override fun databaseHasFoo() = instance.databaseHasFoo()
    override fun requestHttp() = instance.requestHttp()
    override fun assertIsBar() = instance.assertIsBar()
}

class GherkinInstance : GherkinInterpreter {

    private var mutableState = 1

    override fun Given(code: GivenActions.() -> Unit): WhenInitBdd {
        code()
        return this
    }

    override fun When(code: WhenActions.() -> Unit): ThenInitBdd {
        code()
        return this
    }

    override fun Then(code: ThenActions.() -> Unit) {
        code()
    }

    override fun databaseHasFoo() {
        println("db incrementing state")
        mutableState++
    }

    override fun requestHttp() {
        println("request HTTP")
    }

    override fun assertIsBar() {
        println("state: $mutableState")
    }

}

interface GherkinInterpreter : GivenBdd, WhenInitBdd, ThenInitBdd, GivenActions, WhenActions, ThenActions

interface GivenBdd {
    @Suppress("FunctionNaming", "FunctionName")
    fun Given(code: GivenActions.() -> Unit): WhenInitBdd
}

interface WhenInitBdd {
    @Suppress("FunctionNaming", "FunctionName")
    infix fun When(code: WhenActions.() -> Unit): ThenInitBdd
}

interface ThenInitBdd {
    @Suppress("FunctionNaming", "FunctionName")
    infix fun Then(code: ThenActions.() -> Unit)
}

interface GivenActions {
    fun databaseHasFoo()
}

interface WhenActions {
    fun requestHttp()
}

interface ThenActions {
    fun assertIsBar()
}

