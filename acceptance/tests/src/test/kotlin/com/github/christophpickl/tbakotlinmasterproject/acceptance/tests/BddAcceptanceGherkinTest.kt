package com.github.christophpickl.tbakotlinmasterproject.acceptance.tests

import io.kotest.core.spec.style.DescribeSpec

class BddAcceptanceGherkinTest : DescribeSpec() {

    @Suppress("PrivatePropertyName")
    private val Given = GherkinStateManager()
    
    override fun listeners() = listOf(Given)

    init {
        describe("When request HTTP") {
            it("Given database has foo Then is bar A") {
                println("test A")
                Given {
                    databaseHasFoo()
                } When {
                    requestHttp()
                } Then {
                    assertIsBar()
                }
            }
            it("Then is bar B") {
                println("test B")
                Given {
                } When {
                    requestHttp()
                } Then {
                    assertIsBar()
                }
            }
        }
    }
}
