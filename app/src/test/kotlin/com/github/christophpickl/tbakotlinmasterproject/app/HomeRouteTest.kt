package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.handleGet
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.route
import com.github.christophpickl.tbakotlinmasterproject.commons.commonstest.statusShouldBeOk
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class HomeRouteTest : DescribeSpec() {
    
    init {
        route("/") {
            test("Ok and returns greeting") {
                val response = handleGet("/")
    
                response.statusShouldBeOk()
                response.content shouldBe "Hello World"
            }
        }
    }
}
