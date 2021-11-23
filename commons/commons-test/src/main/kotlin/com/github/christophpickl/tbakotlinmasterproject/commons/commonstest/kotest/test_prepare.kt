package com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.kotest

import io.kotest.core.Tag
import io.kotest.core.listeners.ProjectListener
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import mu.KotlinLogging.logger

/*
object DatabaseMigrationListener : LazyTestPreparer(
    tags = setOf(Tags.Database),
    lazySetup = {
    },
    tearDown = {
    }
)

object KotestProjectConfig : AbstractProjectConfig() {
    override fun listeners(): List<Listener> = listOf(DatabaseMigrationListener)
}
*/


/**
 * Will invoke the setup code only if found a test spec with given tag(s).
 * Will invoke the teardown code only if at least one matching test spec was found beforehand.
 */
abstract class LazyTestPreparer(
    /** Watch out: In order for this to work, the tag needs to be added for the spec (=class) not for test itself. */
    tags: Set<Tag>,
    lazyBeforeSpecPreparation: suspend () -> Unit = {},
    lazyAfterProjectPostperation: suspend () -> Unit = {},
    lazyBeforeTestPreparation: suspend (TestCase) -> Unit = {},
    lazyAfterTestPostperation: suspend (TestCase, TestResult) -> Unit = { _, _ -> },
    private val listener: LazyTestPrepareListener = LazyTestPrepareListener(
        tags = tags,
        lazyBeforeSpecPreparation = lazyBeforeSpecPreparation,
        lazyAfterProjectPostperation = lazyAfterProjectPostperation,
        lazyBeforeTestPreparation = lazyBeforeTestPreparation,
        lazyAfterTestPostperation = lazyAfterTestPostperation
    )
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
    private val lazyBeforeSpecPreparation: suspend () -> Unit,
    private val lazyAfterProjectPostperation: suspend () -> Unit,
    private val lazyBeforeTestPreparation: suspend (TestCase) -> Unit = {},
    private val lazyAfterTestPostperation: suspend (TestCase, TestResult) -> Unit = { _, _ -> },
) : TestListener, StatePreparer {

    private val log = logger {}
    private var state = State.Initial

    override suspend fun beforeSpec(spec: Spec) {
        if (spec.isTagged()) {
            state = state.lazyBeforeSpec(this)
        }
    }

    suspend fun afterProject() {
        log.info { "afterProject()" }
        state = state.lazyAfterProject(this)
    }

    override suspend fun lazyBeforeSpecCallback() {
        log.info { "Preparing test suite setup..." }
        lazyBeforeSpecPreparation()
        log.info { "Preparing test suite ... DONE" }
    }

    override suspend fun lazyAfterProjectCallback() {
        log.info { "Tearing down test suite ..." }
        lazyAfterProjectPostperation()
        log.info { "Tearing down test suite ... DONE" }
    }

    override suspend fun beforeTest(testCase: TestCase) {
        if (state == State.SetUp && testCase.spec.isTagged()) {
            lazyBeforeTestPreparation(testCase)
        }
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        if (state == State.SetUp && testCase.spec.isTagged()) {
            lazyAfterTestPostperation(testCase, result)
        }
    }

    private fun Spec.isTagged() =
        tags().any { tags.contains(it) }

}

private enum class State {
    Initial {
        override suspend fun lazyBeforeSpec(preparer: StatePreparer): State {
            preparer.lazyBeforeSpecCallback()
            return SetUp
        }

        override suspend fun lazyAfterProject(preparer: StatePreparer): State {
            // nothing to do
            return this
        }
    },

    SetUp {
        override suspend fun lazyBeforeSpec(preparer: StatePreparer): State {
            // nothing to do
            return this
        }

        override suspend fun lazyAfterProject(preparer: StatePreparer): State {
            preparer.lazyAfterProjectCallback()
            return TornDown
        }
    },

    TornDown {
        override suspend fun lazyBeforeSpec(preparer: StatePreparer): State {
            throw IllegalStateException("Can't set up, was already torn down!")
        }

        override suspend fun lazyAfterProject(preparer: StatePreparer): State {
            throw IllegalStateException("Can't tear down, was already torn down!")
        }
    },
    ;

    abstract suspend fun lazyBeforeSpec(preparer: StatePreparer): State
    abstract suspend fun lazyAfterProject(preparer: StatePreparer): State

}

private interface StatePreparer {
    suspend fun lazyBeforeSpecCallback()
    suspend fun lazyAfterProjectCallback()
}
