package com.github.christophpickl.tbakotlinmasterproject

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class DemoTest: StringSpec() {
    init {
        "foo" {
            (1 + 1) shouldBe 2
        }
    }
}
