dependencies {
    implementation(project(":app"))
    implementation(project(":boundary:boundary-db"))
    implementation(project(":boundary:boundary-db", "test"))
    implementation(project(":commons:commons-lang"))
    implementation(project(":commons:commons-test"))
    implementation(project(":commons:commons-ktor"))
    implementation(Dependencies.Testcontainers.Postgres)

    implementation(Dependencies.Exposed)
    implementation(Dependencies.Ktor.ServerNetty)
    implementation(Dependencies.Ktor.Client)
    implementation(Dependencies.Ktor.ServerCore)
    implementation(Dependencies.Ktor.ServerTestHost)
    implementation(Dependencies.Mockk)
    implementation(Dependencies.Arrow.Core)
    implementation(Dependencies.Kotest.RunnerJunit5)
    implementation(Dependencies.Kotest.AssertionsArrow)
    implementation(Dependencies.Kotest.AssertionsCoreJvm)

    runtimeOnly(Dependencies.Postgres)
}
