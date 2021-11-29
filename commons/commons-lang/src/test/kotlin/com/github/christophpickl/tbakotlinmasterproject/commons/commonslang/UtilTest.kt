package com.github.christophpickl.tbakotlinmasterproject.commons.commonslang

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class UtilTest : DescribeSpec() {
    init {
        describe("readRuntimeVariableOrNull") {
            it("nothing") {
                readRuntimeVariableOrNull("unknown").shouldBeNull()
            }
            it("system") {
                System.setProperty("system", "valueSystem")
                readRuntimeVariableOrNull("system") shouldBe "valueSystem"
            }
        }
    }
}