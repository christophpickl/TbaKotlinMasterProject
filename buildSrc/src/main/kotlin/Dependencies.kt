@Suppress("MayBeConstant")
object Dependencies {
    
    object Kotest {
        private fun kotest(artifactIdSuffix: String) = "io.kotest:kotest-$artifactIdSuffix:${Versions.Kotest}"
        val RunnerJunit5 = kotest("runner-junit5")
        val AssertionsCoreJvm = kotest("assertions-core-jvm")
    }
    
    object Koin {
        private fun koin(artifactIdSuffix: String) = "io.insert-koin:koin-$artifactIdSuffix:${Versions.Koin}"
        val Core = koin("core")
        val Ktor = koin("ktor")
    }
    
    object Ktor {
        private fun ktor(artifactIdSuffix: String) = "io.ktor:ktor-$artifactIdSuffix:${Versions.Ktor}"
        val ServerNetty = ktor("server-netty")
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