dependencies {
    api(Dependencies.Ktor.ServerCore)
    api(Dependencies.Ktor.ServerTestHost)
    api(Dependencies.Mockk)
    api(Dependencies.Arrow.Core)
    api(Dependencies.Kotest.AssertionsArrow)
    api(Dependencies.Kotest.AssertionsCoreJvm)
    implementation(Dependencies.Kotest.RunnerJunit5)
    implementation(Dependencies.Jackson)
}
