dependencies {
    api(Dependencies.Ktor.ServerCore)
    api(Dependencies.Ktor.ServerTestHost)
    api(Dependencies.Mockk)
    implementation(Dependencies.Kotest.RunnerJunit5)
    implementation(Dependencies.Kotest.AssertionsCoreJvm)
    implementation(Dependencies.Jackson)
}
