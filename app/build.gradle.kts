dependencies {
    implementation(Dependencies.Ktor.ServerNetty)
    
    testImplementation(Dependencies.Ktor.ServerTestHost)
    testImplementation(Dependencies.Kotest.RunnerJunit5)
    testImplementation(Dependencies.Kotest.AssertionsCoreJvm)
}
