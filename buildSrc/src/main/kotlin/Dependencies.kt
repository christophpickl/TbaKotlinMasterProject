@Suppress("MayBeConstant", "SameParameterValue")
object Dependencies {
    
    object Arrow {
        private fun arrow(artifactIdSuffix: String) = "io.arrow-kt:arrow-$artifactIdSuffix:${Versions.Arrow}"
        val Core = arrow("core")
    }
    
    val Jackson = "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.Jackson}"
    
    object Kotest {
        private fun kotest(artifactIdSuffix: String, version: String = Versions.Kotest) = "io.kotest:kotest-$artifactIdSuffix:$version"
        val RunnerJunit5 = kotest("runner-junit5")
        val AssertionsCoreJvm = kotest("assertions-core-jvm")
        val AssertionsArrow = kotest("assertions-arrow-jvm", Versions.KotestArrowAssertions)
    }
    
    object Koin {
        private fun koin(artifactIdSuffix: String) = "io.insert-koin:koin-$artifactIdSuffix:${Versions.Koin}"
        val Core = koin("core")
        val Ktor = koin("ktor")
    }
    
    object Ktor {
        private fun ktor(artifactIdSuffix: String) = "io.ktor:ktor-$artifactIdSuffix:${Versions.Ktor}"
        val ServerNetty = ktor("server-netty")
        val ServerCore = ktor("server-core")
        val ServerTestHost = ktor("server-test-host")
    }
    
    object Logging {
        val Logback = "ch.qos.logback:logback-classic:${Versions.Logback}"
        val MuLogging = "io.github.microutils:kotlin-logging:${Versions.MuLogging}"
    }
    
    val Mockk = "io.mockk:mockk:${Versions.Mockk}"

    object Moshi {
        private fun moshi(artifactIdSuffix: String) = "com.squareup.moshi:$artifactIdSuffix:${Versions.Moshi}"
        val Moshi = moshi("moshi")
        val Kotlin = moshi("moshi-kotlin")
        val Ktor = "com.hypercubetools:ktor-moshi-server:${Versions.MoshiKtor}"
    }
}
