dependencies {
    api(Dependencies.Ktor.ServerCore)
    api(Dependencies.Ktor.ServerTestHost)
    api(Dependencies.Mockk)
    api(Dependencies.Arrow.Core)
    api(Dependencies.Kotest.AssertionsArrow)
    api(Dependencies.Kotest.AssertionsCoreJvm)
    api(Dependencies.Kotest.RunnerJunit5)
    api(Dependencies.Kotest.AssertionsJson)

    implementation(Dependencies.Jackson)
}
