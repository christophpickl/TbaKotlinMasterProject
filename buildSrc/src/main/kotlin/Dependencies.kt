@Suppress("MayBeConstant")
object Dependencies {
    
    object Kotest {
        private fun kotest(artifactIdSuffix: String) = "io.kotest:kotest-$artifactIdSuffix:${Versions.Kotest}"
        val RunnerJunit5 = kotest("runner-junit5")
        val AssertionsCoreJvm = kotest("assertions-core-jvm")
    }
    
    object Ktor {
        private fun ktor(artifactIdSuffix: String) = "io.ktor:ktor-$artifactIdSuffix:${Versions.Ktor}"
        val ServerNetty = ktor("server-netty")
        val ServerTestHost = ktor("server-test-host")
    }
    
    val Mockk = "io.mockk:mockk:${Versions.Mockk}"
}
