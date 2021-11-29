dependencies {
    implementation(project(":api:api-model"))
    implementation(project(":boundary:boundary-db"))
    implementation(project(":commons:commons-ktor"))
    implementation(project(":commons:commons-lang"))
    implementation(project(":domain:domain-boundary"))
    implementation(project(":domain:domain-logic"))
    implementation(project(":domain:domain-model"))

    implementation(Dependencies.Ktor.ServerNetty)
    implementation(Dependencies.Koin.Ktor)
    implementation(Dependencies.Arrow.Core)
    implementation(Dependencies.Logging.Logback)

    runtimeOnly(Dependencies.Postgres)

    testImplementation(project(":domain:domain-model", "test"))
    testImplementation(project(":boundary:boundary-db", "test"))
    testImplementation(project(":commons:commons-test"))
}
