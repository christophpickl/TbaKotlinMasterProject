package com.github.christophpickl.tbakotlinmasterproject.domain.domainmodel

import io.kotest.assertions.arrow.validation.shouldBeInvalid
import io.kotest.assertions.arrow.validation.shouldBeValid
import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.row

class TitleTest : DescribeSpec() {
    init {
        describe("instantiation") {
            it("invalid") {
                forAll(
                    row(""),
                    row(" "),
                    row("a "),
                    row(" b"),
                ) { (value: String) ->
                    Title(value).shouldBeInvalid()
                }
            }
            it("valid") {
                forAll(
                    row("a"),
                    row("A"),
                    row("1"),
                    row("!"),
                ) { (value: String) ->
                    Title(value).shouldBeValid()
                }
            }
        }
    }
}
