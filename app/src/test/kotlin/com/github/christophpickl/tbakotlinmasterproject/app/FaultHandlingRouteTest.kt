package com.github.christophpickl.tbakotlinmasterproject.app

import com.github.christophpickl.tbakotlinmasterproject.commonstest.route
import io.kotest.core.spec.style.DescribeSpec
import org.koin.dsl.module

class FaultHandlingRouteTest : DescribeSpec() {
    init {
        route("/test/fault") {
            test("fo", module {
            }) {
            
            }
        }
    }
}

private class TestableFaultHandlingRoute {

}