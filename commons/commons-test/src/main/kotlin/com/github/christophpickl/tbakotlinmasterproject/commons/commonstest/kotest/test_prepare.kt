package com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.kotest

import io.kotest.core.Tag
import io.kotest.core.listeners.ProjectListener
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import mu.KotlinLogging.logger

abstract class LazyTestPreparer(
    tags: Set<Tag>,
    lazySetup: suspend () -> Unit = {},
    tearDown: suspend () -> Unit = {},
    private val listener: LazyTestPrepareListener = LazyTestPrepareListener(tags, lazySetup, tearDown)
) : ProjectListener, TestListener by listener {

    private val log = logger {}

    override val name: String = this::class.java.simpleName

    override suspend fun afterProject() {
        log.info { "afterProject()" }
        listener.afterProject()
    }
}

/** Will register itself via delegated by [LazyTestPreparer]. */
class LazyTestPrepareListener(
    private val tags: Set<Tag>,
    private val lazySetupCallback: suspend () -> Unit,
    private val tearDownCallback: suspend () -> Unit
) : TestListener, StatePreparer {

    private val log = logger {}
    private var state = State.Initial

    override suspend fun beforeSpec(spec: Spec) {
        if(spec.tags().any { tags.contains(it) }) {
            state = state.setUp(this)
        }
    }

//    override suspend fun beforeTest(testCase: TestCase) {
//        if(testCase.spec.tags().any { tags.contains(it)}) {
//
//        }
//    }

    suspend fun afterProject() {
        log.info { "afterProject()" }
        state = state.tearDown(this)
    }

    override suspend fun setUp() {
        log.info { "Running test setup..." }
        lazySetupCallback()
        log.info { "Running test setup ... DONE" }
    }

    override suspend fun tearDown() {
        log.info { "Running test teardown ..." }
        tearDownCallback()
        log.info { "Running test teardown ... DONE" }
    }
}

private enum class State {
    Initial {
        override suspend fun setUp(preparer: StatePreparer): State {
            preparer.setUp()
            return SetUp
        }

        override suspend fun tearDown(preparer: StatePreparer): State {
            // nothing to do
            return this
        }
    },

    SetUp {
        override suspend fun setUp(preparer: StatePreparer): State {
            // nothing to do
            return this
        }

        override suspend fun tearDown(preparer: StatePreparer): State {
            preparer.tearDown()
            return TornDown
        }
    },

    TornDown {
        override suspend fun setUp(preparer: StatePreparer): State {
            throw IllegalStateException("Can't set up, was already torn down!")
        }

        override suspend fun tearDown(preparer: StatePreparer): State {
            throw IllegalStateException("Can't tear down, was already torn down!")
        }
    },
    ;

    abstract suspend fun setUp(preparer: StatePreparer): State
    abstract suspend fun tearDown(preparer: StatePreparer): State
}

private interface StatePreparer {
    suspend fun setUp()
    suspend fun tearDown()
}
