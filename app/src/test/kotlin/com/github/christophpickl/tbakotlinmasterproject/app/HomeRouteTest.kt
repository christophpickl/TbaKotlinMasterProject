package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.commonstest.handleGet
import com.github.christophpickl.tbakotlinmasterproject.commonstest.route
import com.github.christophpickl.tbakotlinmasterproject.commonstest.statusShouldBeOk
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class HomeRouteTest : DescribeSpec() {
    
    private val title = "test title"
    
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
